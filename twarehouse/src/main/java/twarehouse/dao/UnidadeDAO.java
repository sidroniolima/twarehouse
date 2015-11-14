/**
 * 
 */
package twarehouse.dao;

import twarehouse.model.Unidade;

/**
 * Camada DAO da entidade Unidade.
 * 
 * @author Sidronio
 *
 * 22/10/2015
 */
public interface UnidadeDAO extends GenericDAO<Unidade, Long>{

	/**
	 * Retorna o número de produtos que utilizam a unidade como 
	 * de entrada ou saída.
	 * 
	 * @param unidade
	 * @return
	 */
	public Long buscarQtdDeProdutosComAUnidade(Unidade unidade);
	
}
