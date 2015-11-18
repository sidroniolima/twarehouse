/**
 * 
 */
package twarehouse.controller;

import java.util.List;

import twarehouse.util.Paginator;

/**
 * Interface para listagem de dados simples.
 * 
 * @author Sidronio
 * 18/11/2015
 */
public abstract class ListaSimple<E> {

	/**
	 * Inicia o Controller.
	 */
	public abstract void init();
	
	/**
	 * Lista os dados com paginação. 
	 */
	public abstract void listarComPaginacao();
	
	/**
	 * Retorna o paginator utilizado na view.
	 * 
	 * @return
	 */
	public abstract Paginator getPaginator();

	/**
	 * Retorna os dados da listagem.
	 * 
	 * @return
	 */
	public abstract List<E> getListaDeDados();
	
}
