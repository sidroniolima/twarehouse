/**
 * 
 */
package twarehouse.dao.hibernate;

import javax.ejb.Stateless;

import twarehouse.dao.CompraDAO;
import twarehouse.model.Compra;

/**
 * Implementação da camada DAO para a entidade Compra utilizando 
 * o framework Hibernate.
 * 
 * @author Sidronio
 * 26/11/2015
 */
@Stateless
public class HibernateCompraDAO extends HibernateGenericDAO<Compra, Long> implements CompraDAO{

}
