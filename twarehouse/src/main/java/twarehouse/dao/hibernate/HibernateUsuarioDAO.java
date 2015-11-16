/**
 * 
 */
package twarehouse.dao.hibernate;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.UsuarioDAO;
import twarehouse.model.Usuario;

/**
 * Implementação da camada DAO da entidade Usuario.
 * 
 * @author Sidronio
 *
 */
@Stateless
public class HibernateUsuarioDAO extends HibernateGenericDAO<Usuario, Long> implements UsuarioDAO {

	/* Retorna um usuário buscando pelo Login e Senha.
	 * @see twarehouse.dao.UsuarioDAO#buscarPorLoginESenha(java.lang.String, java.lang.String)
	 */
	@Override
	public Usuario buscarPorLoginESenha(String email, String senha) {
		
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("senha", senha));
		
		return (Usuario) criteria.uniqueResult();
	}

	/* Retorna um usuário e suas permissões.
	 * @see twarehouse.dao.UsuarioDAO#buscarComPermissoes(java.lang.Long)
	 */
	@Override
	public Usuario buscarComPermissoes(Long codigo) {
		
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("codigo", codigo));
		
		criteria.setFetchMode("permissoes", FetchMode.JOIN);
		
		return (Usuario) criteria.uniqueResult();
	}

}
