/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Produto;

/**
 * Interface de acesso da camada DAO.
 * 
 * @author Sidronio
 * 
 * 22/10/2015
 */
public interface ProdutoDAO extends GenericDAO<Produto, Long>{

	/**
	 * Fornece uma lista de Produtos instanciados do primeiro 
	 * registro até a quantidade definida por página. 
	 * 
	 * @param firstResult
	 * @param qtdPorPagina
	 * @return
	 */
	public List<Produto> listaComPaginacao(int firstResult, int qtdPorPagina);
}
