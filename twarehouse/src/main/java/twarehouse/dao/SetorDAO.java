/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Funcionario;
import twarehouse.model.Setor;

/**
 * Interface DAO da entidade Setor.
 * 
 * @author Sidronio
 *
 * 26/10/2015
 */
public interface SetorDAO extends GenericDAO<Setor, Long>{

	/**
	 * Lista os funcionários cadastrados para o setor.
	 * 
	 * @param setor
	 * @return
	 */
	public List<Funcionario> listarFuncionariosDoSetor(Setor setor);
}
