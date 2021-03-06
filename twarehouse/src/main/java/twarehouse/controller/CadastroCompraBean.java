/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.factory.DocumentoEntradaFactory;
import twarehouse.model.Compra;
import twarehouse.model.DocumentoEntrada;
import twarehouse.model.Fornecedor;
import twarehouse.model.ItemCompra;
import twarehouse.model.Produto;
import twarehouse.model.TipoDocumentoEntrada;
import twarehouse.service.CompraService;
import twarehouse.service.impl.FornecedorService;
import twarehouse.service.impl.ProdutoService;
import twarehouse.util.FacesUtil;

/**
 * Camada Controller para o cadastro de Compras.
 * 
 * @author Sidronio
 * 26/11/2015
 */
@Named
@ViewScoped
public class CadastroCompraBean extends CadastroMasterDetail implements Serializable {

	private static final long serialVersionUID = -3865901777087859471L;

	@Inject
	private CompraService compraService;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private FornecedorService fornecedorService;	
	
	@Param @Inject
	private Long paramCodigo;
	
	private Compra compra;
	private ItemCompra itemCompra;
	private ItemCompra itemCompraSelecionado;
	
	private List<Produto> produtos = new ArrayList<Produto>();
	private List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
	
	private TipoDocumentoEntrada[] tiposDocumentos;
	
	@Override
	@PostConstruct
	void init() {

		if (isEdicao()) {
			
			compra = compraService.buscaPeloCodigoCompleta(paramCodigo);
			compra.salvaEstado();
			
		} else {
			
			compra = new Compra();
		}
		
		novoItem();
		
		produtos = produtoService.listaTodos();
		fornecedores = fornecedorService.buscaTodos();
		
		tiposDocumentos = TipoDocumentoEntrada.values();
		
	}

	@Override
	void novoRegistro() {
		compra = new Compra();
		novoItem();
	}
	
	/**
	 * Atribui um objeto Documento à Compra, utilizando 
	 * uma fábrica de objetos que retorna o tipo: 
	 * 
	 * @param tipo
	 */
	public void criaDocumento(String tipo) {
		compra.adicionaDocumento(DocumentoEntradaFactory.cria(TipoDocumentoEntrada.valueOf(tipo)));
	}

	@Override
	public void salvar() {
		
		try {

			compraService.salva(compra);
			
			if (isEdicao()) {
				
				FacesUtil.addSuccessMessage(this.getMensagemDeAlteracao(compra.toString()));
			}else {
				
				FacesUtil.addSuccessMessage(this.getMensagemDeInclusao(compra.toString()));
			}
			
			novoRegistro();
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@Override
	public boolean isEdicao() {
		return null != paramCodigo;
	}

	@Override
	String getMensagemDeInclusao(String registro) {
		return String.format("Compra %s salva com sucesso!", registro);
	}

	@Override
	String getMensagemDeAlteracao(String registro) {
		return String.format("Compra %s alterada com sucesso!", registro);
	}
	
	@Override
	public boolean isEdicaoDeItem() {
		return null != itemCompraSelecionado;
	}

	@Override
	public void editaItem() {
		itemCompra = itemCompraSelecionado;
	}

	@Override
	String getMensagemDeInclusaoDeItem(String registro) {
		return String.format("Item %s incluído.", registro);
	}

	@Override
	String getMensagemDeAlteracaoDeItem(String registro) {
		return String.format("Item %s alterado.", registro);
	}

	@Override
	String getMensagemDeExclusaoDeItem(String registro) {
		return String.format("Item %s removido.", registro);
	}

	@Override
	public void adicionaItem() {
		
		try {
			
			itemCompra.valida();
			
			if (this.isEdicaoDeItem()) {
				
				FacesUtil.addSuccessMessage(getMensagemDeAlteracaoDeItem(itemCompra.toString()));
			} else {
				
				compra.adicionaItem(itemCompra);
				
				FacesUtil.addSuccessMessage(getMensagemDeInclusaoDeItem(itemCompra.toString()));
			}
			
			novoItem();

		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}

	@Override
	public void removeItem() {
		
		try {
			this.compra.removeItem(itemCompraSelecionado.getProduto());
			
			FacesUtil.addSuccessMessage(getMensagemDeExclusaoDeItem(itemCompraSelecionado.toString()));
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@Override
	void novoItem() {
		itemCompraSelecionado = null;
		itemCompra = new ItemCompra();
	}

	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public ItemCompra getItemCompra() {
		return itemCompra;
	}
	public void setItemCompra(ItemCompra itemCompra) {
		this.itemCompra = itemCompra;
	}

	public ItemCompra getItemCompraSelecionado() {
		return itemCompraSelecionado;
	}
	public void setItemCompraSelecionado(ItemCompra itemCompraSelecionado) {
		this.itemCompraSelecionado = itemCompraSelecionado;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public TipoDocumentoEntrada[] getTiposDocumentos() {
		return tiposDocumentos;
	}
	
}
