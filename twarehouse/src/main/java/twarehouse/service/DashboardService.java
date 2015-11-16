/**
 * 
 */
package twarehouse.service;

import java.math.BigInteger;

/**
 * Interface da camada Service para o Controller do 
 * Dashboard.
 * 
 * @author Sidronio
 *
 * 16/11/2105
 */
public interface DashboardService {

	/**
	 * Faz uma requisição ao Service do Estoque para 
	 * fornecer a quantidade de produtos esgotados.
	 * 
	 * @return
	 */
	public BigInteger qtdDeProdutosEsgotados();
	
	/**
	 * Requisita ao Service do Estoque para 
	 * que atingiram o limite de reposição.
	 * 
	 * @return
	 */
	public BigInteger qtdDeProdutosEmReposicao();
}
