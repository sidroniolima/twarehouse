/**
 * 
 */
package twarehouse.service;

import twarehouse.model.Compra;

/**
 * Interface da camada Service do padrãpo MVC para 
 * a entidade Compra.
 * 
 * @author Sidronio
 * 26/11/2105
 */
public interface CompraService extends SimpleServiceLayer<Compra, Long>{

	/**
	 * Busca um registro de compra pelo código com os 
	 * relacionamentos de Fornecedor, Itens e Documento.
	 * 
	 * @param paramCodigo Código da compra.
	 * @return Compra localizada.
	 */
	Compra buscaPeloCodigoCompleta(Long paramCodigo);

}
