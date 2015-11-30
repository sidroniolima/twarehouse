/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Compra;
import twarehouse.model.Fornecedor;
import twarehouse.model.Produto;
import twarehouse.model.TipoDocumentoEntrada;
import twarehouse.model.consulta.FiltroEntrada;
import twarehouse.service.CompraService;
import twarehouse.service.impl.FornecedorService;
import twarehouse.service.impl.ProdutoService;
import twarehouse.util.FacesUtil;
import twarehouse.util.IConstants;
import twarehouse.util.Paginator;

/**
 * Classe da camada Controller responsável pela view 
 * da entidade compra. Lista os registros de forma paginada, 
 * fornece o link para alteração de um registro e permite a 
 * exclusão.
 * 
 * @author Sidronio
 * 30/11/2015
 * 
 */
@Named
@ViewScoped
public class PesquisaCompraBean extends PesquisaSingle implements Serializable {

	private static final long serialVersionUID = 7110965047476423057L;

	@Inject
	private CompraService compraService;
	
	@Inject
	private FornecedorService fornecedorService;
	
	@Inject
	private ProdutoService produtoService;
	
	private Compra compraSelecionada;
	private List<Compra> compras;
	
	private Paginator paginator;
	
	private FiltroEntrada filtro;
	
	private List<Fornecedor> fornecedores;
	private List<Produto> produtos;
	private TipoDocumentoEntrada[] tiposDeDocumentos;
	
	@PostConstruct
	@Override
	public void init() {
		
		paginator = new Paginator(IConstants.QTD_PADRAO_POR_PAGINA);
		listarComPaginacao();
		
		fornecedores = fornecedorService.buscaTodos();
		produtos = produtoService.listaTodos();
		tiposDeDocumentos = TipoDocumentoEntrada.values();
		
		filtro = new FiltroEntrada();
		
	}
	
	@Override
	public void excluir() {
		
		try {
			
			compraService.exclui(compraSelecionada.getCodigo());
			
			compras.remove(compraSelecionada);
			
			FacesUtil.addSuccessMessage(this.getMensagemDeExclusaoOk(compraSelecionada.toString()));
		
		} catch (RegraDeNegocioException e) {

			FacesUtil.addSuccessMessage(
					this.getMensagemDeErroDeExclusao(
							compraSelecionada.toString(),
							e.getMessage()));
		}
		
	}

	@Override
	public void listarComPaginacao() {
		
		if (null == filtro) {
			
			compras = compraService.listaComPaginacao(
					paginator, 
					Arrays.asList("documento.data"), 
					Arrays.asList("fornecedor","fornecedor.pessoa","documento"), 
					Arrays.asList("documento"));
		} else {
			
			compras = compraService.filtraPelaPesquisa(filtro, paginator);
		}
		
	}

	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("%s excluído(a) com sucesso.", registro);
	}

	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		return String.format("Não foi possível excluir o(a) %s.", registro, msgError);
	}

	public Compra getCompraSelecionada() {
		return compraSelecionada;
	}
	public void setCompraSelecionada(Compra compraSelecionada) {
		this.compraSelecionada = compraSelecionada;
	}

	public Paginator getPaginator() {
		return paginator;
	}
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public FiltroEntrada getFiltro() {
		return filtro;
	}
	public void setFiltro(FiltroEntrada filtro) {
		this.filtro = filtro;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public TipoDocumentoEntrada[] getTiposDeDocumentos() {
		return tiposDeDocumentos;
	}
	
}
