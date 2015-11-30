/**
 * 
 */
package twarehouse.dao.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.CompraDAO;
import twarehouse.model.Compra;
import twarehouse.model.consulta.FiltroEntrada;

/**
 * Implementação da camada DAO para a entidade Compra utilizando 
 * o framework Hibernate.
 * 
 * @author Sidronio
 * 26/11/2015
 */
@Stateless
public class HibernateCompraDAO extends HibernateGenericDAO<Compra, Long> implements CompraDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<Compra> filtrarPeloModoEspecifico(FiltroEntrada filtro, int firstResult, int qtdDeRegistros) {

		Criteria criteria = getSession().createCriteria(Compra.class);

		criteria.setFetchMode("fornecedor", FetchMode.JOIN);
		criteria.setFetchMode("fornecedor.pessoa", FetchMode.JOIN);
		criteria.setFetchMode("documento", FetchMode.JOIN);
		criteria.setFetchMode("itens", FetchMode.JOIN);

		criteria.createAlias("documento", "doc");
		criteria.createAlias("itens", "itens");
		
		if (null != filtro.getDataFinal()) {
			
			criteria.add(Restrictions.between("doc.data", filtro.getDataInicial(), filtro.getDataFinal()));
			
		} else {
		
			if (null != filtro.getDataInicial()) {
				criteria.add(Restrictions.eq("doc.data", filtro.getDataInicial()));
			}
		}
		
		if (null != filtro.getTipo()) {
			criteria.add(Restrictions.eq("doc.class", filtro.getTipo().toString()));
		}
		
		if (null != filtro.getFornecedor()) {
			criteria.add(Restrictions.eq("fornecedor", filtro.getFornecedor()));
		}
		
		if (null != filtro.getProduto()) {
			criteria.add(Restrictions.eq("itens.produto", filtro.getProduto()));
		}		
		
		criteria.addOrder(Order.asc("doc.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(qtdDeRegistros);
		
		return criteria.list();
	}

}
