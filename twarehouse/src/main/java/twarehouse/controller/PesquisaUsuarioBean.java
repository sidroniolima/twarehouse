/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Usuario;
import twarehouse.service.UsuarioService;
import twarehouse.util.FacesUtil;
import twarehouse.util.IConstants;
import twarehouse.util.Paginator;

/**
 * Controller de pesquisa responsável pela listagem 
 * e exclusão dos registros de Usuários.
 * 
 * @author Sidronio
 * 16/11/2015
 */
@Named
@ViewScoped
public class PesquisaUsuarioBean extends PesquisaSingle implements Serializable{

	private static final long serialVersionUID = -1433476152199318623L;

	@Inject
	private UsuarioService usuarioService;
	
	private Usuario usuarioSelecionado;
	
	private List<Usuario> usuarios;
	
	private Paginator paginator;
	
	@PostConstruct
	@Override
	public void init() {
		paginator = new Paginator(IConstants.QTD_PADRAO_POR_PAGINA);
		listarComPaginacao();
	}

	@Override
	public void excluir() {
		
		try {
			usuarioService.exclui(usuarioSelecionado.getCodigo());
			
			FacesUtil.addSuccessMessage(this.getMensagemDeExclusaoOk(usuarioSelecionado.getNome()));
		
			usuarios.remove(usuarioSelecionado);
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addSuccessMessage(this.getMensagemDeErroDeExclusao(usuarioSelecionado.getNome(), e.getMessage()));
		}
	}

	@Override
	public void listarComPaginacao() {
		usuarios = usuarioService.listaComPaginacao(
				paginator, 
				Arrays.asList("nome"), 
				Arrays.asList("permissoes"), 
				null);
	}

	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("Usuário %s excluído com sucesso.", registro);
	}

	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		return String.format("Não foi possível excluir o usuário %s. %s", registro, msgError);
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public Paginator getPaginator() {
		return paginator;
	}
}
