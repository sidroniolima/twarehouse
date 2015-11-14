/**
 * 
 */
package twarehouse.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.FuncionarioDAO;
import twarehouse.model.Funcionario;

/**
 * Implementa a camada DAO para a entidade Funcionário.
 * 
 * @author Sidronio
 * 26/10/2015
 */
@Stateless
public class HibernateFuncionarioDAO extends HibernateGenericDAO<Funcionario, Long> implements FuncionarioDAO, Serializable {

	private static final long serialVersionUID = -8826321640856212761L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Funcionario> listarComSetorEPaginacao(int firstResult, int qtdPorPagina) {
		Criteria criteriaSublist = getSession().createCriteria(Funcionario.class);
		
		criteriaSublist.setProjection(Projections.property("codigo"));
		criteriaSublist.setFirstResult(firstResult);
		criteriaSublist.setMaxResults(qtdPorPagina);
		
		criteriaSublist.addOrder(Order.asc("nome"));
		
		List funcionariosSublist = criteriaSublist.list();
		
		if (funcionariosSublist.isEmpty()) {
			return null;
		}
		
		Criteria criteria = getSession().createCriteria(Funcionario.class);
		criteria.setFetchMode("setor", FetchMode.JOIN);
		
		criteria.add(Restrictions.in("codigo", funcionariosSublist));
		
		criteria.addOrder(Order.asc("nome"));
		
		return criteria.list();
	}

}
