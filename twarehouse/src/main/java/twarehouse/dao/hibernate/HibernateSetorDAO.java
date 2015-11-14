/**
 * 
 */
package twarehouse.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.SetorDAO;
import twarehouse.model.Funcionario;
import twarehouse.model.Setor;

/**
 * Implementação da interface SetorDAO.
 * 
 * @author Sidronio
 * 26/10/2015
 * 
 */
@Stateless
public class HibernateSetorDAO extends HibernateGenericDAO<Setor, Long> implements SetorDAO, Serializable {

	private static final long serialVersionUID = -4725506470885144407L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> listarFuncionariosDoSetor(Setor setor) {
		
		Criteria criteria = this.getSession().createCriteria(Funcionario.class);
		
		criteria.add(Restrictions.eq("setor", setor));
		
		criteria.addOrder(Order.asc("nome"));
		
		return criteria.list();
	}

}
