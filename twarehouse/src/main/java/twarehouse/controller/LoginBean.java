package twarehouse.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import twarehouse.model.Usuario;
import twarehouse.service.impl.LoginService;
import twarehouse.util.FacesUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 5253282195667350015L;

	@Inject
	private LoginService loginService;
	
	@Produces
	private Usuario usuarioLogado;
	
	private Usuario usuario = new Usuario();
	
	public String login() {
		
		
		try {
			
			loginService.validaLogin(usuario.getEmail(), usuario.getSenha());
			
			UsernamePasswordToken token = new UsernamePasswordToken(usuario.getEmail(), usuario.getSenha());
			SecurityUtils.getSubject().login(token);
			
			usuarioLogado = this.loginService.buscaUsuarioPorEmailESenha(usuario.getEmail(), usuario.getSenha());

			return "/private/dashboard.xhtml?faces-redirect=true";
		} catch (AuthenticationException ex) {
			
			FacesUtil.addErrorMessage("Email ou senha inválidos.");
			return "";
		}
	}
	
	public String logout() {
		loginService.logout();
		
		return "login";
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
}
