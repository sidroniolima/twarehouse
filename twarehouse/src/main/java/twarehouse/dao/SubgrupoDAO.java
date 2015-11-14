/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Produto;
import twarehouse.model.Subgrupo;

/**
 * @author Sidronio
 *
 */
public interface SubgrupoDAO extends GenericDAO<Subgrupo, Long>{

	/**
	 * Retorna do banco os produtos relacionados 
	 * ao subgrupo.
	 * 
	 * @param subgrupo
	 * @return
	 */
	public List<Produto> buscarProdutosDoSubgrupo(Subgrupo subgrupo);
}
