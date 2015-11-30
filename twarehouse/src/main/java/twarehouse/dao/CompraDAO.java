/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Compra;
import twarehouse.model.consulta.FiltroEntrada;

/**
 * Interface de acesso a camada DAO da entidade Compra.
 * 
 * @author Sidronio
 * 26/11/2015
 */
public interface CompraDAO extends GenericDAO<Compra, Long>{

	/**
	 * Filtra os registros de Compra pelo filtro específico 
	 * da pesquisa.
	 * 
	 * @param filtro Filtro específico de compra.
	 * @param firstResult Primeiro registro.
	 * @param qtdDeRegistros Quantiade de registros a serem exibidos.
	 * @return Registros filtrados que atenderam o filtro.
	 */
	List<Compra> filtrarPeloModoEspecifico(FiltroEntrada filtro, int firstResult, int qtdDeRegistros);

}
