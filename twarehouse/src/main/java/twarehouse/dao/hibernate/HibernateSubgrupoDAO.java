/**
 * 
 */
package twarehouse.dao.hibernate;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.SubgrupoDAO;
import twarehouse.model.Produto;
import twarehouse.model.Subgrupo;

/**
 * @author Sidronio
 *
 */
@Stateless
public class HibernateSubgrupoDAO extends HibernateGenericDAO<Subgrupo, Long> implements SubgrupoDAO {

	/* 
	 * (non-Javadoc)
	 * @see twarehouse.dao.SubgrupoDAO#buscarProdutosDoSubgrupo(twarehouse.model.Produto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> buscarProdutosDoSubgrupo(Subgrupo subgrupo) {
		
		Criteria criteria = this.getSession().createCriteria(Produto.class);
		
		criteria.add(Restrictions.eq("subgrupo", subgrupo));
		
		return criteria.list();
	}

}
