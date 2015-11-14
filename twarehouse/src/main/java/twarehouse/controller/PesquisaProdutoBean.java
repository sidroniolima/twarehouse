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

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.service.impl.ProdutoService;
import twarehouse.util.FacesUtil;
import twarehouse.util.IConstants;
import twarehouse.util.Paginator;

/**
 * Camada de visualização do padrão MVC para  
 * pesquisa da entidade Produto.
 * 
 * @author Sidronio
 *
 */
@Named
@ViewScoped
public class PesquisaProdutoBean extends PesquisaSingle implements Serializable {

	private static final long serialVersionUID = 1038730206190990798L;

	private Produto produtoSelecionado;
	
	private List<Produto> produtos;
	
	private Paginator paginator;
	
	@Inject
	private ProdutoService produtoService;
	
	@PostConstruct
	@Override
	public void init() {
		paginator = new Paginator(IConstants.QTD_DE_PRODUTOS_POR_PAGINA);
		
		listarComPaginacao();
	}

	@Override
	public void listarComPaginacao() {
		produtos = produtoService.listaComPaginacao(paginator);
	}
	
	@Override
	public void excluir() {
		
		try {
			
			produtoService.exclui(produtoSelecionado);
			
			produtos.remove(produtoSelecionado);
			
			FacesUtil.addSuccessMessage(this.getMensagemDeExclusaoOk(produtoSelecionado.getDescricao()));
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(
					this.getMensagemDeErroDeExclusao(produtoSelecionado.getDescricao(), e.getMessage()));
		}
	}

	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("Produto %s excluído com sucesso.", registro);
	}

	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		return String.format("Não foi possível excluir o produto %s. %s", registro, msgError);
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public Paginator getPaginator() {
		return paginator;
	}

}
