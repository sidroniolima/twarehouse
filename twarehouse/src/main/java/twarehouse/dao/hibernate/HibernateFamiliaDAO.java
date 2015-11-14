/**
 * 
 */
package twarehouse.dao.hibernate;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.FamiliaDAO;
import twarehouse.model.Familia;
import twarehouse.model.Grupo;

/**
 * @author Sidronio
 *
 */
@Stateless
public class HibernateFamiliaDAO extends HibernateGenericDAO<Familia, Long> implements FamiliaDAO {

	/* (non-Javadoc)
	 * @see twarehouse.dao.FamiliaDAO#buscaGruposDaFamilia(twarehouse.model.Familia)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> buscaGruposDaFamilia(Familia familia) {
		
		Criteria criteria = this.getSession().createCriteria(Grupo.class);
		criteria.add(Restrictions.eq("familia", familia));
		
		return criteria.list();
	}

}
