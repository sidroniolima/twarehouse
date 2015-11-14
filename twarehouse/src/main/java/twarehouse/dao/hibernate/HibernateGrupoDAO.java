/**
 * 
 */
package twarehouse.dao.hibernate;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.GrupoDAO;
import twarehouse.model.Grupo;
import twarehouse.model.Subgrupo;

/**
 * Implementação Hibernate do DAO da entidade Grupo.
 * 
 * @author Sidronio
 * 30/09/2015
 * 
 */
@Stateless
public class HibernateGrupoDAO extends HibernateGenericDAO<Grupo, Long> implements GrupoDAO {

	/* (non-Javadoc)
	 * @see twarehouse.dao.GrupoDAO#bucarSubgruposDoGrupo(twarehouse.model.Grupo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> bucarSubgruposDoGrupo(Grupo grupo) {
		Criteria criteria = this.getSession().createCriteria(Subgrupo.class);
		
		criteria.add(Restrictions.eq("grupo", grupo));
		
		return criteria.list();
	}

}
