/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Familia;
import twarehouse.model.Grupo;

/**
 * @author Sidronio
 *
 */
public interface FamiliaDAO extends GenericDAO<Familia, Long> {

	/**
	 * Busca no banco os Grupos relacionados à 
	 * uma Família.
	 * 
	 * @param familia
	 * @return
	 */
	List<Grupo> buscaGruposDaFamilia(Familia familia);

}
