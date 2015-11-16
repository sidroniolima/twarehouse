/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import twarehouse.dao.UsuarioDAO;
import twarehouse.model.Usuario;

/**
 * @author Sidronio
 *
 */
@Stateless
public class LoginService implements Serializable {

	private static final long serialVersionUID = 258998499185545991L;

	@Inject
	private UsuarioDAO usuarioDAO;
	
	/**
	 * Responsável pela autenticação do usuário
	 * 
	 * @param email
	 * @param senha
	 * @throws AuthenticationException
	 */
	public void login(String email, String senha) throws AuthenticationException  {
		UsernamePasswordToken token = new UsernamePasswordToken(email, senha);
		SecurityUtils.getSubject().login(token);
	}

	/**
	 * Desconecta o usuário logado.
	 */
	public void logout() {
		
		if (SecurityUtils.getSubject().isAuthenticated()) {
					
			SecurityUtils.getSubject().logout();
					
		}		
	}

	/**
	 * Busca no banco um usuário que coincida email e senha.
	 * 
	 * @param email
	 * @param senha
	 * @return
	 */
	public Usuario buscaUsuarioPorEmailESenha(String email, String senha) {
		
		Usuario usuario = usuarioDAO.buscarPorLoginESenha(email, senha);
		
		if (null == usuario) {
			return null;
		}
		
		return usuario;
	}

	/**
	 * Busca no banco o usuário corresponde ao nome com as permissões.
	 * 
	 * @param nome
	 * @return
	 */
	public Usuario buscarComPermissoesPeloNome(String nome) {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		
		List<Usuario> filtro =
				usuarioDAO.filtrar(usuario, Arrays.asList("nome"), Arrays.asList("permissao"));
		
		if (null == filtro || filtro.isEmpty()) {
			return null;
		}
		
		return filtro.get(0);
	}

	/**
	 * Valida o email e a senha do login
	 * @param usuario
	 */
	public void validaLogin(String email, String senha) throws AuthenticationException{
		if (email.isEmpty() | senha.isEmpty()) {
			
			throw new AuthenticationException("Email ou senha em branco.");
		}
	}
	
}
