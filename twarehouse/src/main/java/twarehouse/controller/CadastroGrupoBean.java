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
import twarehouse.model.Familia;
import twarehouse.model.Grupo;
import twarehouse.service.impl.FamiliaService;
import twarehouse.service.impl.GrupoService;
import twarehouse.util.FacesUtil;

/**
 * Controller responsável pelo cadastro 
 * da entidade Grupo.
 * 
 * @author Sidronio
 * 30/09/2105
 */
@Named
@ViewScoped
public class CadastroGrupoBean implements Serializable {

	private final String MSG_INCLUSAO = "Grupo %s incluído com sucesso.";
	
	private final String MSG_ALTERACAO = "Grupo %s alterado com sucesso.";
	
	private static final long serialVersionUID = 2345114125758205420L;
	
	@Inject
	private GrupoService grupoService;
	
	@Inject
	private FamiliaService familiaService;
	
	private Grupo grupo;
	
	@Inject @Param
	private Long paramCodigo;
	
	private List<Familia> familias;
	
	/**
	 * Inicia o grupo ou pesquisa pelo código se 
	 * passado por parâmetro.
	 * Lista as famílias disponíveis no cadastro.
	 */
	@PostConstruct
	public void init() {
		
		this.grupo = new Grupo();
		
		this.familias = familiaService.listaTodas();
		
		if (null != paramCodigo) {
			this.grupo = grupoService.buscaPeloCodigoComFamilia(paramCodigo);
		}
		
	}
	
	/**
	 * Valida e salva a instância. 
	 */
	public void salvar() {
		String message = MSG_INCLUSAO;
		
		try {
			
			this.grupoService.salva(grupo);
			
			if (isEdicao()) {
				message = MSG_ALTERACAO;
			}
			
			FacesUtil.addSuccessMessage(
					String.format(message, grupo.getDescricao()));
			
			this.grupo = new Grupo();
			
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
		return null != this.grupo.getCodigo();
	}

	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Long getParamCodigo() {
		return paramCodigo;
	}
	public void setParamCodigo(Long paramCodigo) {
		this.paramCodigo = paramCodigo;
	}
	
	public List<Familia> getFamilias() {
		return familias;
	}
	
}
