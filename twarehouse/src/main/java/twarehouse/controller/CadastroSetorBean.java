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
import twarehouse.model.Setor;
import twarehouse.service.impl.SetorService;
import twarehouse.util.FacesUtil;

/**
 * Responsável pela camada Controller da entidade Setor.
 * 
 * @author Sidronio
 * 26/20/2015
 */
@Named
@ViewScoped
public class CadastroSetorBean extends CadastroSingle implements Serializable {

	private static final long serialVersionUID = -3252674083773996946L;

	@Inject
	private SetorService setorService;
	
	private Setor setor;
	
	@Inject @Param
	private Long paramCodigo;
	
	@Override
	@PostConstruct
	public void init() {
		
		novoRegistro();
		
		if (isEdicao()) {
			setor = setorService.buscaPeloCodigo(paramCodigo);
		}
	}

	@Override
	public void novoRegistro() {
		setor = new Setor();
	}
	
	@Override
	public void salvar() {
		try {
			
			this.setorService.salva(setor);
			
			if (isEdicao()) {
				FacesUtil.addSuccessMessage(this.getMensagemDeAlteracao(setor.getNome()));
				
			} else {
				FacesUtil.addSuccessMessage(this.getMensagemDeInclusao(setor.getNome()));
			}
			
			novoRegistro();
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(String.format("Não foi possível salvar o registro. %s", e.getMessage()));
		}
	
	}

	@Override
	boolean isEdicao() {
		return null != this.paramCodigo;
	}

	@Override
	public String getMensagemDeInclusao(String registro) {
		return String.format("Setor %s incluído com sucesso.", registro);
	}

	@Override
	public String getMensagemDeAlteracao(String registro) {
		return String.format("Setor %s alterado com sucesso.", registro);
	}

	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}

}
