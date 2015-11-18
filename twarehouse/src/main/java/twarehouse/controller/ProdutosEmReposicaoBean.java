/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.model.consulta.ProdutoEmReposicao;
import twarehouse.service.EstoqueService;
import twarehouse.util.IConstants;
import twarehouse.util.Paginator;

/**
 * Implementação da listagem dos produtos que 
 * atingiram o limite de reposição.
 * 
 * @author Sidronio
 * 18/11/2015
 */
@Named
@RequestScoped
public class ProdutosEmReposicaoBean extends ListaSimple<ProdutoEmReposicao> implements Serializable {

	private static final long serialVersionUID = 1733576529992904912L;
	
	@Inject
	private EstoqueService estoqueService;
	
	private List<ProdutoEmReposicao> produtos;
	
	private Paginator paginator;
	
	@PostConstruct
	@Override
	public void init() {
		
		paginator = new Paginator(IConstants.QTD_DE_PRODUTOS_POR_PAGINA);
		this.listarComPaginacao();
	}

	@Override
	public void listarComPaginacao() {
		produtos = estoqueService
				.listaProdutosEmReposicao(getPaginator().getFirstResult(), getPaginator().getQtdPorPagina());
	}

	@Override
	public Paginator getPaginator() {
		return paginator;
	}

	@Override
	public List<ProdutoEmReposicao> getListaDeDados() {
		return produtos;
	}

}
