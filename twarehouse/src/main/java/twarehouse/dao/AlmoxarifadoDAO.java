package twarehouse.dao;

import twarehouse.model.estoque.Almoxarifado;

public interface AlmoxarifadoDAO extends GenericDAO<Almoxarifado, Long> {

	/**
	 * Calcula a quantidade de movimentação do Almoxarifado
	 * 
	 * @param almoxarifado
	 * @return O número de movimentação. 
	 */
	public boolean almoxarifadoTemMovimentacao(Almoxarifado almoxarifado);

}
