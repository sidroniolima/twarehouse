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
import twarehouse.model.Unidade;
import twarehouse.service.UnidadeService;
import twarehouse.util.FacesUtil;

/**
 * Controller responsável pelo cadastro e edição da entidade Unidade.
 * 
 * @author Sidronio
 * 11/11/2015
 * 
 */
@Named
@ViewScoped
public class CadastroUnidadeBean extends CadastroSingle implements Serializable {

	private static final long serialVersionUID = -3010111353713128667L;

	@Inject
	private UnidadeService unidadeService;
	
	private Unidade unidade;
	
	@Param @Inject
	private Long paramCodigo;
	
	@PostConstruct
	@Override
	void init() {
		
		if (isEdicao()) {
			
			unidade = unidadeService.buscaPeloCodigo(paramCodigo);
		} else {
			
			novoRegistro();
		}
		
	}

	@Override
	void novoRegistro() {
		unidade = new Unidade();
	}

	@Override
	public void salvar() {
		
		try {
			
			unidadeService.salva(unidade);
			
			if (isEdicao()) {
				
				FacesUtil.addSuccessMessage(getMensagemDeAlteracao(unidade.getDescricao()));
			} else {
				FacesUtil.addSuccessMessage(getMensagemDeInclusao(unidade.getDescricao()));
			}
			
			novoRegistro();
			
		} catch (RegraDeNegocioException e) {

			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}

	@Override
	public boolean isEdicao() {
		return null != paramCodigo;
	}

	@Override
	String getMensagemDeInclusao(String registro) {
		return String.format("Unidade %s cadastrada com sucesso.", registro);
	}

	@Override
	String getMensagemDeAlteracao(String registro) {
		return String.format("Unidade %s alterada com sucesso.", registro);
	}

	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
}
