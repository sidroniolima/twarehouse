package twarehouse.service;

import java.io.Serializable;
import java.util.List;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.util.Paginator;

/**
 * Interface para a camada Service com os métodos básicos.
 * 
 * @author Sidronio
 * 
 * 05/11/2015
 * 
 * @param <T>
 * @param <ID>
 */
public interface SimpleServiceLayer<T, ID extends Serializable> {

	/**
	 * Salva uma instância.
	 * 
	 * @param entidade
	 * @throws RegraDeNegocioException
	 */
	public void salva(T entidade) throws RegraDeNegocioException;
	
	/**
	 * Exclui uma instância pelo código.
	 * 
	 * @param id
	 * @throws RegraDeNegocioException
	 */
	public void exclui(ID id) throws RegraDeNegocioException;
	
	/**
	 * Retorna um registro salvo pelo Código.
	 * 
	 * @param id
	 * @return
	 */
	public T buscaPeloCodigo(ID id);
	
	/**
	 * Retorna todos os registros.
	 * 
	 * @return
	 */
	public List<T> buscaTodas();
	
	/**
	 * Valida a inserção de registros.
	 * 
	 * @param entidade
	 * @throws RegraDeNegocioException
	 */
	public void validaInsercao(T entidade) throws RegraDeNegocioException;
	
	/**
	 * Valida a exclusão.
	 * 
	 * @param entidade
	 * @throws RegraDeNegocioException
	 */
	public void validaExclusao(T entidade) throws RegraDeNegocioException;
	
	/**
	 * Lista os registros obedecendo a paginação, isto é, o primeiro registro 
	 * e a quantidade deles por página.
	 * 
	 * @param paginator Objeto que informa a quantadide de registros por página e
	 * 					o primeiro a ser exibido.
	 * @param ordenacao
	 * @param relacionamentos
	 * @param aliases
	 * @return Lista de objetos.
	 */
	public List<T> listaComPaginacao(Paginator paginator, List<String> ordenacao, List<String> relacionamentos, List<String> aliases);
	
	/**
	 * Apenas listar as entidades
	 * 
	 * @param entidade
	 * @return Lista de registros cadastrados
	 */
	public List<T> lista();
}
