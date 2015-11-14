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
import twarehouse.molde.estoque.Almoxarifado;
import twarehouse.service.AlmoxarifadoService;
import twarehouse.util.FacesUtil;

/**
 * Classe de implementação da camada controller da entidade 
 * Almoxarifado responsável pelo seu cadastro e edição.
 * 
 * @author Sidronio
 * 06/11/2015
 */
@Named
@ViewScoped
public class CadastroAlmoxarifadoBean extends CadastroSingle implements Serializable {

	@Inject
	private AlmoxarifadoService almoxarifadoService;

	@Inject @Param
	private Long paramCodigo;
	
	private Almoxarifado almoxarifado;

	/* Inicia os métodos e atributos do Bean.
	 * @see twarehouse.controller.CadastroSingle#init()
	 */
	@Override
	@PostConstruct
	public void init() {
		
		if (isEdicao()) {
			
			almoxarifado = almoxarifadoService.buscaPeloCodigo(paramCodigo);
		} else {
			novoRegistro();
		}
	}

	/* Instância um registro.
	 * @see twarehouse.controller.CadastroSingle#novoRegistro()
	 */
	@Override
	public void novoRegistro() {
		almoxarifado = new Almoxarifado();
	}	
	
	/* Salva a instância.
	 * @see twarehouse.controller.CadastroSingle#salvar()
	 */
	@Override
	public void salvar() {
		
		try {
			almoxarifadoService.salva(almoxarifado);
			
			if (isEdicao()) {
				FacesUtil.addSuccessMessage(getMensagemDeAlteracao(almoxarifado.getDescricao()));
			} else {
				FacesUtil.addSuccessMessage(getMensagemDeInclusao(almoxarifado.getDescricao()));
			}
			
			novoRegistro();			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(String.format("Não foi possível incluir o Almoxarifado %s. %s", 
							almoxarifado.getDescricao(), e.getMessage()));
		}
		
	}

	/* Indica se é uma edição de registro.
	 * @see twarehouse.controller.CadastroSingle#isEdicao()
	 */
	@Override
	public boolean isEdicao() {
		return paramCodigo != null;
	}

	/* Mensagem de inclusão.
	 * @see twarehouse.controller.CadastroSingle#getMensagemDeInclusao(java.lang.String)
	 */
	@Override
	public String getMensagemDeInclusao(String registro) {
		return String.format("Almoxarifado %s incluído com sucesso.", almoxarifado.getDescricao());
	}

	/* Mensagem de alteração.
	 * @see twarehouse.controller.CadastroSingle#getMensagemDeAlteracao(java.lang.String)
	 */
	@Override
	public String getMensagemDeAlteracao(String registro) {
		return String.format("Almoxarifado %s alterado com sucesso.", almoxarifado.getDescricao());
	}

	public Almoxarifado getAlmoxarifado() {
		return almoxarifado;
	}
	public void setAlmoxarifado(Almoxarifado almoxarifado) {
		this.almoxarifado = almoxarifado;
	}
	
}
