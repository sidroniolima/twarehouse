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
import twarehouse.model.Familia;
import twarehouse.service.impl.FamiliaService;
import twarehouse.util.FacesUtil;

/**
 * Bean responsável pelo cadastro de uma Familia.
 * 
 * @author Sidronio
 * 28/09/2015
 */
@Named
@ViewScoped
public class CadastroFamiliaBean implements Serializable {

	private final String MSG_INCLUSAO = "Família %s incluída com sucesso.";
	
	private final String MSG_ALTERACAO = "Família %s alterada com sucesso.";
	
	private static final long serialVersionUID = 4506892775427397755L;

	@Inject
	private FamiliaService familiaService;
	
	@Inject @Param
	private Long paramCodigo;
	
	private Familia familia = new Familia();
	
	@PostConstruct
	public void init() {
		if (null != this.paramCodigo) {
			familia = familiaService.buscaPeloCodigo(paramCodigo);
		}
	}
	
	/**
	 * Chama o método do serviço responsável por persistir 
	 * a entidade e exibe mensagem na tela.
	 */
	public void salvar(){
		
		String message = MSG_INCLUSAO;
		
		try {
			this.familiaService.salva(familia);
			
			if (isEdicao()) {
				message = MSG_ALTERACAO;
			}
			
			FacesUtil.addSuccessMessage(
					String.format(message, familia.getDescricao()));
			
		    this.familia = new Familia();

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
		return null != this.familia.getCodigo();
	}

	public Familia getFamilia() {
		return familia;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

}
