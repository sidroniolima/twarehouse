package twarehouse.dao;

import twarehouse.model.Usuario;

/**
 * Interface da camada DAO da entidade Usuario.
 * 
 * @author Sidronio
 *
 */
public interface UsuarioDAO extends GenericDAO<Usuario, Long>{
	
	/**
	 * Busca um usuário pelo login e senha.
	 * 
	 * @param email
	 * @param senha
	 * @return
	 */
	public Usuario buscarPorLoginESenha(String email, String senha);
	
	/**
	 * Retorna as informações do usuário e suas permissões.
	 * 
	 * @param codigo
	 * @return
	 */
	public Usuario buscarComPermissoes(Long codigo);
	
}
