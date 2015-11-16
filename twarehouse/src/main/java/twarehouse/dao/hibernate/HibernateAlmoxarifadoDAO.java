package twarehouse.dao.hibernate;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.AlmoxarifadoDAO;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.model.estoque.Movimento;

@Stateless
public class HibernateAlmoxarifadoDAO extends HibernateGenericDAO<Almoxarifado, Long> implements AlmoxarifadoDAO {

	/* Calcula a quantidade de movimentação.
	 * @see twarehouse.dao.AlmoxarifadoDAO#qtdDeMovimentacao(twarehouse.molde.estoque.Almoxarifado)
	 */
	@Override
	public boolean almoxarifadoTemMovimentacao(Almoxarifado almoxarifado) {
		
		Criteria criteria = getSession().createCriteria(Movimento.class);
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Restrictions.eq("almoxarifado", almoxarifado));
		
		return ((Long) criteria.uniqueResult()) > 0;
	}

}
