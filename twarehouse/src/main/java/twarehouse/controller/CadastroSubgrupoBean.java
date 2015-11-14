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

import org.omnifaces.cdi.Param;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Grupo;
import twarehouse.model.Subgrupo;
import twarehouse.service.impl.GrupoService;
import twarehouse.service.impl.SubgrupoService;
import twarehouse.util.FacesUtil;

/**
 * Controller responsável pelo cadastro 
 * da entidade Subgrupo.
 * 
 * @author Sidronio
 * 30/09/2105
 */
@Named
@ViewScoped
public class CadastroSubgrupoBean implements Serializable {
	
	private final String MSG_INCLUSAO = "Subrupo %s incluído com sucesso.";
	
	private final String MSG_ALTERACAO = "Subrupo %s alterado com sucesso.";
	
	private static final long serialVersionUID = 2345114125758205420L;
	
	@Inject
	private GrupoService grupoService;
	
	@Inject
	private SubgrupoService subgrupoService;
	
	private Subgrupo subgrupo;
	
	@Inject @Param
	private Long paramCodigo;
	
	private List<Grupo> grupos;
	
	/**
	 * Inicia o subgrupo ou pesquisa pelo código se 
	 * passado por parâmetro.
	 * Lista os grupos disponíveis no cadastro.
	 */
	@PostConstruct
	public void init() {
		
		this.subgrupo = new Subgrupo();
		
		this.grupos = grupoService.listaTodos();
		
		if (isEdicao()) {
			this.subgrupo = subgrupoService.buscaPeloCodigoComGrupo(paramCodigo);
		}
		
	}
	
	/**
	 * Valida e salva a instância. 
	 */
	public void salvar() {
		String message = MSG_INCLUSAO;
		
		try {
			
			this.subgrupoService.salva(subgrupo);
			
			if (isEdicao()) {
				message = MSG_ALTERACAO;
			}
			
			FacesUtil.addSuccessMessage(
					String.format(message, subgrupo.getDescricao()));
			
			this.subgrupo = new Subgrupo();
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}
	
	/**
	 * Indica se a ação é de alteração de um registro 
	 * já persistido.
	 * 
	 * @return
	 */
	private boolean isEdicao() {
		return null != paramCodigo;
	}

	public Subgrupo getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(Subgrupo subgrupo) {
		this.subgrupo = subgrupo;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

}
