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
import twarehouse.model.Familia;
import twarehouse.service.impl.FamiliaService;
import twarehouse.util.FacesUtil;

/**
 * Classe responsável pela listagem dos registros da 
 * entidade Familia e controles de edição e exclusão.
 * 
 * @author Sidronio
 *
 * 28/09/2015
 */
@Named
@ViewScoped
public class PesquisaFamiliaBean implements Serializable {

	private static final long serialVersionUID = -2710461974350036486L;

	private static final String MSG_EXCLUSAO_OK = "Família %s excluída com sucesso.";

	private static final String MSG_EXCLUSAO_ERRO = "Problema ao excluir a Família %s. ";

	@Inject
	private FamiliaService familiaService;
	
	private List<Familia> familias;
	
	private Familia familiaSelecionada;
	
	/**
	 * Carrega os registros para a variável familias
	 */
	@PostConstruct
	public void init() {
		if (null == familias) {
			familias = this.familiaService.listaTodas();
		}
	}
	
	public void excluir() {
			try {
				this.familiaService.exclui(familiaSelecionada);

				this.familias.remove(familiaSelecionada);
				
				FacesUtil.addSuccessMessage(
						String.format(MSG_EXCLUSAO_OK, familiaSelecionada.getDescricao()));
				
			} catch (RegraDeNegocioException e) {
				FacesUtil.addErrorMessage(
						String.format(MSG_EXCLUSAO_ERRO + e.getMessage(), familiaSelecionada.getDescricao()));
			}
			

	}
	
	public List<Familia> getFamilias() {
		return familias;
	}

	public Familia getFamiliaSelecionada() {
		return familiaSelecionada;
	}
	public void setFamiliaSelecionada(Familia familiaSelecionada) {
		this.familiaSelecionada = familiaSelecionada;
	}
	
}
