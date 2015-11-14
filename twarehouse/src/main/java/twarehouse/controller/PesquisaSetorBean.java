package twarehouse.controller;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.controller.PesquisaSingle;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Setor;
import twarehouse.service.impl.SetorService;
import twarehouse.util.FacesUtil;

/**
 * 
 */

/**
 * Controller para a pesquisa da Entidade Setor.
 * 
 * @author Sidronio
 * 26/10/2015
 */
@Named
@ViewScoped
public class PesquisaSetorBean extends PesquisaSingle implements Serializable {

	private static final long serialVersionUID = 4068064034110272780L;

	@Inject
	private SetorService setorService;
	
	private Setor setorSelecionado;
	private List<Setor> setores;
	
	@Override
	@PostConstruct
	public void init() {
		
		if (null == setores) {
			setores = setorService.listaTodos();
		}
	}

	@Override
	public void excluir() {
		
		try {
			setorService.exclui(setorSelecionado);
			
			setores.remove(setorSelecionado);
			
			FacesUtil.addSuccessMessage(
					this.getMensagemDeExclusaoOk(setorSelecionado.getNome()));
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(
					this.getMensagemDeErroDeExclusao(setorSelecionado.getNome(), e.getMessage()));
		}
		
	}

	@Override
	public void listarComPaginacao() {
		
	}

	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("Setor %s excluído com sucesso.", registro);
	}

	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		 return String.format("Não foi possível excluir o setor %s. %s", registro, msgError);
	}

	public Setor getSetorSelecionado() {
		return setorSelecionado;
	}
	public void setSetorSelecionado(Setor setorSelecionado) {
		this.setorSelecionado = setorSelecionado;
	}

	public List<Setor> getSetores() {
		return setores;
	}

}
