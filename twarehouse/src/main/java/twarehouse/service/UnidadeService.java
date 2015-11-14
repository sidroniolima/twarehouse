/**
 * 
 */
package twarehouse.service;

import twarehouse.model.Unidade;

/**
 * Interface Serviço para a entidade Unidade.
 * 
 * @author Sidronio
 * 11/05/2015
 */
public interface UnidadeService extends SimpleServiceLayer<Unidade, Long> {

	
	/**
	 * Verifica se a unidade possui produtos que a utilize, 
	 * seja como entrada ou saída.
	 * 
	 * @param unidade
	 * @return
	 */
	public boolean temProdutoComAUnidade(Unidade unidade);
	
}
