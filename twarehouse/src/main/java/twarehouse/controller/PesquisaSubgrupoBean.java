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
import twarehouse.model.Subgrupo;
import twarehouse.service.impl.SubgrupoService;
import twarehouse.util.FacesUtil;

/**
 * @author Sidronio
 *
 */
@Named
@ViewScoped
public class PesquisaSubgrupoBean implements Serializable{

	private static final long serialVersionUID = 4406830181417643185L;
	
	private static final String MSG_EXCLUSAO_OK = "Subgrupo %s excluído com sucesso.";
	private static final String MSG_EXCLUSAO_ERRO = "O subgrupo %s não pode ser excluído. ";
	
	@Inject
	private SubgrupoService subgrupoService;
	
	private Subgrupo subgrupoSelecionado;

	private List<Subgrupo> subgrupos;
	
	@PostConstruct
	public void init() {
		
		if (null == this.subgrupos) {
			subgrupoSelecionado = new Subgrupo();
			subgrupoSelecionado.setDescricao("Teste");
			this.subgrupos = subgrupoService.listaTodosComGrupo();
		}
	}

	/**
	 * Exclui a instância selecionada caso atenda
	 * à validação.
	 * 
	 * @param subgrupo
	 * @throws RegraDeNegocioException
	 */
	public void excluir() {
		
		try {
			this.subgrupoService.exclui(subgrupoSelecionado);
			
			
			this.subgrupos.remove(subgrupoSelecionado);
			
			FacesUtil.addSuccessMessage(
					String.format(MSG_EXCLUSAO_OK, subgrupoSelecionado.getDescricao()));
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(
					String.format(MSG_EXCLUSAO_ERRO + e.getMessage(), subgrupoSelecionado.getDescricao()));
		}

	}

	public Subgrupo getSubgrupoSelecionado() {
		return subgrupoSelecionado;
	}
	public void setSubgrupoSelecionado(Subgrupo subgrupoSelecionado) {
		this.subgrupoSelecionado = subgrupoSelecionado;
	}

	public List<Subgrupo> getSubgrupos() {
		return subgrupos;
	}

}
