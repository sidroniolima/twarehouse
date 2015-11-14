/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Grupo;
import twarehouse.service.impl.GrupoService;
import twarehouse.util.FacesUtil;

/**
 * @author Sidronio
 *
 */
@Named
@ViewScoped
public class PesquisaGrupoBean implements Serializable{

	private static final long serialVersionUID = 4406830181417643185L;
	
	private static final String MSG_EXCLUSAO_OK = "Grupo %s excluído com sucesso.";
	private static final String MSG_EXCLUSAO_ERRO = "O Grupo %s não pode ser excluído. ";
	
	@Inject
	private GrupoService grupoService;
	
	private Grupo grupoSelecionado;

	private List<Grupo> grupos;
	
	@PostConstruct
	public void init() {
		
		if (null == this.grupos) {
			this.grupos = grupoService.listaTodosComFamilia();
		}
	}

	/**
	 * Exclui a instância selecionada caso atenda
	 * à validação.
	 * 
	 * @param grupo
	 * @throws RegraDeNegocioException
	 */
	public void excluir() {
		
		try {
			this.grupoService.excluir(grupoSelecionado);
			
			this.grupos.remove(grupoSelecionado);
			FacesUtil.addSuccessMessage(String.format(MSG_EXCLUSAO_OK, grupoSelecionado.getDescricao()));
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(
					String.format(MSG_EXCLUSAO_ERRO + e.getMessage(), grupoSelecionado.getDescricao()));
		}
		
		
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}
	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

}
