/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.model.Subgrupo;
import twarehouse.model.Unidade;
import twarehouse.service.UnidadeService;
import twarehouse.service.impl.ProdutoService;
import twarehouse.service.impl.SubgrupoService;
import twarehouse.util.FacesUtil;

/**
 * @author Sidronio
 *
 */
@Named
@ViewScoped
public class CadastroProdutoBean extends CadastroSingle implements Serializable {

	private static final long serialVersionUID = -2903479882310365510L;

	private Produto produto;
	
	private List<Subgrupo> subgrupos;
	private List<Unidade> unidades;
	
	@Inject @Param
	private Long paramCodigo;
	
	@Inject
	private ProdutoService produtoService;
	@Inject
	private SubgrupoService subgrupoService;
	@Inject
	private UnidadeService unidadeService;
	
	@PostConstruct
	@Override
	public void init() {
		
		if (isEdicao()) {
			produto = produtoService.buscaPeloCodigoComSubgrupoEUnidades(paramCodigo);
		} else {
			
			novoRegistro();
		}
		
		this.subgrupos = subgrupoService.listaTodas();
		this.unidades = unidadeService.buscaTodas();
	}

	@Override
	public void novoRegistro() {
		produto = new Produto();
	}

	@Override
	public void salvar() {
		String message = this.getMensagemDeInclusao(produto.getDescricao());
		
		if (isEdicao()) {
			message = this.getMensagemDeAlteracao(produto.getDescricao());
		}
		
		try {
			
			this.produtoService.salva(produto);
			
			FacesUtil.addSuccessMessage(message);
			
			novoRegistro();
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}

	@Override
	boolean isEdicao() {
		return null != paramCodigo;
	}

	@Override
	public String getMensagemDeInclusao(String registro) {
		return String.format("Produto %s incluído com sucesso.", registro);
	}

	@Override
	public String getMensagemDeAlteracao(String registro) {
		return String.format("Produto %s alterado com sucesso.", registro);
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Subgrupo> getSubgrupos() {
		return subgrupos;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}
}
