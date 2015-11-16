/**
 * 
 */
package twarehouse.service;

import twarehouse.model.estoque.Almoxarifado;

/**
 * @author Sidronio
 *
 */
public interface AlmoxarifadoService extends SimpleServiceLayer<Almoxarifado, Long> {

	/**
	 * Busca o almoxarifado principal de acordo com parâmetro. 
	 * 
	 * @return Almoxarifado
	 */
	public Almoxarifado buscaAlmoxarifadoPrincipal();
}
