/**
 * 
 */
package twarehouse.dao.hibernate;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.ProdutoDAO;
import twarehouse.model.Produto;

/**
 * Implementação do DAO da entidade Produto.
 * 
 * @author Sidronio
 * 22/10/2015
 *
 */
@Stateless
public class HibernateProdutoDAO extends HibernateGenericDAO<Produto, Long> implements ProdutoDAO{

	/* (non-Javadoc)
	 * @see twarehouse.dao.ProdutoDAO#listaComPaginacao(int, int)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Produto> listaComPaginacao(int firstResult, int qtdPorPagina) {
		
		Criteria criteriaSublist = getSession().createCriteria(Produto.class);
		
		criteriaSublist.setProjection(Projections.property("codigo"));
		criteriaSublist.setFirstResult(firstResult);
		criteriaSublist.setMaxResults(qtdPorPagina);
		
		criteriaSublist.addOrder(Order.asc("codigo"));
		
		List produtosSublist = criteriaSublist.list();
		
		if (produtosSublist.isEmpty()) {
			return null;
		}
		
		Criteria criteria = getSession().createCriteria(Produto.class);
		criteria.setFetchMode("subgrupo", FetchMode.JOIN);
		
		criteria.add(Restrictions.in("codigo", produtosSublist));
		
		criteria.addOrder(Order.asc("codigo"));
		
		return criteria.list();
	}

}
