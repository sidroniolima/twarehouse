/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Permissao;
import twarehouse.model.Usuario;
import twarehouse.service.UsuarioService;
import twarehouse.util.FacesUtil;

/**
 * Controller responsável pelo cadastro e edição de 
 * instâncias de Usuario.
 * 
 * @author Sidronio
 * 16/11/2105
 */
@Named
@ViewScoped
public class CadastroUsuarioBean extends CadastroSingle implements Serializable{

	private static final long serialVersionUID = -41088889823851953L;
	
	@Inject
	private UsuarioService usuarioService;
	
	private Usuario usuario;
	
	@Inject @Param
	private Long paramCodigo;
	
	private Permissao[] permissoes;

	/* (non-Javadoc)
	 * @see twarehouse.controller.CadastroSingle#init()
	 */
	@PostConstruct
	@Override
	void init() {
		
		if (isEdicao()) {
			
			usuario = usuarioService.buscaUsuarioComPermissoes(paramCodigo);
			
		} else {
			
			novoRegistro();
		}
		
		permissoes = Permissao.getValues();
		
	}

	@Override
	void novoRegistro() {
		usuario = new Usuario();
	}

	@Override
	public void salvar() {
		
		try {
		
			usuarioService.salva(usuario);
			
			if (isEdicao()) {
				
				FacesUtil.addSuccessMessage(this.getMensagemDeAlteracao(usuario.getNome()));
				
			} else {
			
				FacesUtil.addSuccessMessage(this.getMensagemDeInclusao(usuario.getNome()));
			}
			
			novoRegistro();
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@Override
	boolean isEdicao() {
		return null != paramCodigo;
	}

	@Override
	String getMensagemDeInclusao(String registro) {
		return String.format("Usuário %s incluído com sucesso.", registro);
	}

	@Override
	String getMensagemDeAlteracao(String registro) {
		return String.format("Usuário %s alterado com sucesso.", registro);
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Permissao[] getPermissoes() {
		return permissoes;
	}

}
