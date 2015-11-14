/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Funcionario;

/**
 * Interface da entidade Funcionário.
 * 
 * @author Sidronio
 * 26/10/2015
 * 
 */
public interface FuncionarioDAO extends GenericDAO<Funcionario, Long>{

	/**
	 * Lista os funcionários com o setor utilizando paginação.
	 * @return 
	 */
	List<Funcionario> listarComSetorEPaginacao(int firstResult, int resultsPerPage);

}
