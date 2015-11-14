package twarehouse.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.GenericDAO;

public class HibernateGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID>{

	@PersistenceContext
	private EntityManager manager;
	
	private Class<T> classeDaEntidade;
	
	@SuppressWarnings("unchecked")
	public HibernateGenericDAO() {
		
		classeDaEntidade = (Class<T>) 
				( (ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public void salvar(T entidade) {
		manager.merge(entidade);
	}

	@Override
	public void excluir(ID id) {
		T entidade = buscarPeloCodigo(id);
		
		manager.remove(entidade);
		
		manager.flush();
		manager.clear();
	}

	@Override
	public T buscarPeloCodigo(ID id) {
		return manager.find(classeDaEntidade, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> filtrar(T entidade, List<String> propriedades, List<String> relacionamentos) {
		
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(classeDaEntidade);
		
		if (propriedades != null) {
			
			for (String propriedade : propriedades) {
				
				try {
					
					Object valor = PropertyUtils.getProperty(entidade, propriedade);
					
					if (null != valor) {
						
						if (propriedade.equals("nome") || propriedade.equals("descricao")) {
							
							criteria.add(Restrictions.ilike(propriedade, valor.toString(), MatchMode.ANYWHERE));
						} else {
						
							criteria.add(Restrictions.eq(propriedade, valor));
						}
					}
					
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					
					throw new RuntimeException("Propriedade não encontrada");
				}
			}
			
		}
		
		if (null != relacionamentos) {
		
			for (String relacionamento : relacionamentos) {
				
				criteria.setFetchMode(relacionamento, FetchMode.JOIN);
			} 
		}
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}

	protected EntityManager getManager() {
		return manager;
	}
	
	protected Session getSession() {
		return manager.unwrap(Session.class);
	}
	
	protected Class<T> getClasseDaEntidade() {
		return classeDaEntidade;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listarComPaginacao(int firstResult, int numberPerPage, 
			List<String> ordenacao, List<String> relacionamentos, List<String> aliases) {
		
		Session session = manager.unwrap(Session.class);
		Criteria criteriaSublist = session.createCriteria(classeDaEntidade);
		
		criteriaSublist.setProjection(Projections.property("codigo"));
		criteriaSublist.setFirstResult(firstResult);
		criteriaSublist.setMaxResults(numberPerPage);
		
		if (null != relacionamentos) {
			for (String relacionamento : relacionamentos) {
				criteriaSublist.setFetchMode(relacionamento, FetchMode.JOIN);
			} 
		}		
		
		if (null != aliases) {
			for (String alias : aliases) {
				criteriaSublist.createAlias(alias, alias);
			} 
		}		
				
		if (null != ordenacao) {
			ordenacao.forEach(o -> criteriaSublist.addOrder(Order.asc(o)));
		}	
		
		List registrosSublist = criteriaSublist.list();
		
		if (registrosSublist.isEmpty()) {
			return null;
		}
		
		Criteria criteria = session.createCriteria(classeDaEntidade);
		
		if (null != relacionamentos) {
			for (String relacionamento : relacionamentos) {
				criteria.setFetchMode(relacionamento, FetchMode.JOIN);
			} 
		}
		
		if (null != aliases) {
			for (String alias : aliases) {
				criteria.createAlias(alias, alias);
			} 
		}			
		
		if (null != ordenacao) {
			ordenacao.forEach(o -> criteria.addOrder(Order.asc(o)));
		}
		
		criteria.add(Restrictions.in("codigo", registrosSublist));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
