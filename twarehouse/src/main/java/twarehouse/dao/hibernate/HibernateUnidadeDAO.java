/**
 * 
 */
package twarehouse.dao.hibernate;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.UnidadeDAO;
import twarehouse.model.Produto;
import twarehouse.model.Unidade;

/**
 * Implementação da classe DAO da entidade Unidade.
 * 
 * @author Sidronio
 *
 * 22/10/2015
 *
 */
@Stateless
public class HibernateUnidadeDAO extends HibernateGenericDAO<Unidade, Long> implements UnidadeDAO {

	@Override
	public Long buscarQtdDeProdutosComAUnidade(Unidade unidade) {
		
		Criteria criteria = getSession().createCriteria(Produto.class);
		
		criteria.setProjection(Projections.rowCount());
		
		criteria.setFetchMode("unidades", FetchMode.JOIN);
		criteria.createAlias("unidades", "unidades");
		
		criteria.add(
				Restrictions.or(
						Restrictions.eq("unidades.entrada", unidade), 
					Restrictions.eq("unidades.saida", unidade)));
		
		return (Long) criteria.uniqueResult();
	}

}
