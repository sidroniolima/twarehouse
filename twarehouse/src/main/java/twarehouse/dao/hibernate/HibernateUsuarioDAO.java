/**
 * 
 */
package twarehouse.dao.hibernate;

import javax.ejb.Stateless;

import twarehouse.dao.UsuarioDAO;
import twarehouse.model.Usuario;

/**
 * @author Sidronio
 *
 */
@Stateless
public class HibernateUsuarioDAO extends HibernateGenericDAO<Usuario, Long> implements UsuarioDAO {

}
