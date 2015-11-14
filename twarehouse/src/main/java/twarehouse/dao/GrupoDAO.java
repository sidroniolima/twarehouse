/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Grupo;

/**
 * Interface DAO da entidade Grupo.
 * 
 * @author Sidronio
 * 30/09/2015
 */
public interface GrupoDAO extends GenericDAO<Grupo, Long>{

	/**
	 * Retorna do banco os subgrupo relacionados 
	 * ao Grupo pesquisado.
	 * 
	 * @param grupo
	 * @return
	 */
	List<Grupo> bucarSubgruposDoGrupo(Grupo grupo);

}
