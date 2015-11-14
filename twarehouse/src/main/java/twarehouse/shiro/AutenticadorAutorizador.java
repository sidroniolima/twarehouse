package twarehouse.shiro;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import twarehouse.model.Permissao;
import twarehouse.model.Usuario;
import twarehouse.service.impl.LoginService;

public class AutenticadorAutorizador extends AuthorizingRealm {

	private LoginService loginService;

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return true;
	}
	
	
	private LoginService getLoginService() {
		
		Properties props = new Properties(); 
		
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
	    try {
	    	
	        InitialContext ctx = new InitialContext(props);
	        
	        LoginService loginService = (LoginService) ctx.lookup("java:module/LoginService");
	        
	        return loginService;
	        
	    } catch(NamingException e) { 
	    	
	        throw new RuntimeException(e);
	    }
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		if (principals == null) {
			throw new AuthorizationException("Principals is missing...");
		}
		
		String nome = (String) principals.getPrimaryPrincipal();
		
		loginService = this.getLoginService();
		
		Usuario usuario = loginService.buscarComPermissoesPeloNome(nome);
		
		if (null == usuario) {
			
			throw new AuthorizationException("Usuário não existe.");
		}
		
		if (!usuario.temPermissoes()) {
			
			throw new AuthorizationException("Não há permissões associadas ao usuário.");
		}
		
		Set<String> roles = new HashSet<String>();
		
		for (Permissao permissao : usuario.getPermissoes()) {
			
			roles.add(permissao.getDescricao());
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		info.setStringPermissions(roles);
		
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken usernameToken = (UsernamePasswordToken) token;
		
		String email = usernameToken.getUsername();
		String senha = new String(usernameToken.getPassword());
		
		loginService = this.getLoginService();
		Usuario usuario = loginService.buscaUsuarioPorEmailESenha(email, senha);
		
		System.out.println("Nome ou email:" + email);
		
		if (null != usuario) {
			AuthenticationInfo info = new SimpleAuthenticationInfo(email, senha, getName());
			
			return info;
		}
		
		throw new AuthenticationException();
	}
	
}
