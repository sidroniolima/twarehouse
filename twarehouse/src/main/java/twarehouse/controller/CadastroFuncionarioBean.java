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
import twarehouse.model.Funcionario;
import twarehouse.model.Operadora;
import twarehouse.model.Setor;
import twarehouse.model.Telefone;
import twarehouse.service.impl.FuncionarioService;
import twarehouse.service.impl.SetorService;
import twarehouse.util.FacesUtil;

/**
 * Camada de controller responsável pela inclusão e alteração 
 * de entidades Funcionário.
 * 
 * @author Sidronio
 * 30/10/2015
 */
@Named
@ViewScoped
public class CadastroFuncionarioBean extends CadastroMasterDetail implements Serializable {

	private static final long serialVersionUID = -3404527688397027631L;

	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private SetorService setorService;
	
	@Inject @Param
	private Long paramCodigo;
	
	private Funcionario funcionario;
	
	private List<Setor> setores;
	private Operadora[] operadoras;
	
	private Telefone telefone;
	private Telefone telefoneSelecionado;
	
	@PostConstruct
	@Override
	public void init() {
		
		if (null == setores) {
			setores = setorService.listaTodos();
		}
		
		if (null == operadoras) {
			operadoras = Operadora.values();
		}
		
		if (isEdicao()) {
			funcionario = funcionarioService.buscaPeloCodigoComSetorETelefones(paramCodigo);
			
			novoItem();
		} else {
			novoRegistro();
		}
		
	}

	@Override
	public void novoRegistro() {
		funcionario = new Funcionario();
		
		novoItem();
	}
	
	@Override
	public void salvar() {
		
		try {
			
			funcionarioService.salva(funcionario);
			
			if (isEdicao()) {
				FacesUtil.addSuccessMessage(this.getMensagemDeAlteracao(funcionario.getNome()));
			} else {
				FacesUtil.addSuccessMessage(this.getMensagemDeInclusao(funcionario.getNome()));
			}
			
			novoRegistro();
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}

	@Override
	boolean isEdicao() {
		return null != paramCodigo;
	}

	@Override
	public String getMensagemDeInclusao(String registro) {
		return String.format("Funcionário %s incluído com sucesso.", funcionario.getNome());
	}

	@Override
	public String getMensagemDeAlteracao(String registro) {
		return String.format("Funcionário %s alterado com sucesso.", funcionario.getNome());
	}

	@Override
	public boolean isEdicaoDeItem() {
		return telefoneSelecionado == telefone;
	}

	@Override
	public String getMensagemDeInclusaoDeItem(String registro) {
		return String.format("Telefone %s adicionado ao funcionário.", registro);
	}

	@Override
	public String getMensagemDeAlteracaoDeItem(String registro) {
		return String.format("Telefone %s alterado.", registro);
	}

	@Override
	public String getMensagemDeExclusaoDeItem(String registro) {
		return String.format("O telefone %s foi removido do funcionário.", registro);
	}

	@Override
	public void adicionaItem() {
		
		try {
			
			telefone.valida();
			
			if (!isEdicaoDeItem()) {
				funcionario.adicionaTelefone(telefone);
				
				FacesUtil.addSuccessMessage(this.getMensagemDeInclusaoDeItem(telefone.getNumero()));
				
			} else {
				FacesUtil.addSuccessMessage(this.getMensagemDeAlteracaoDeItem(telefone.getNumero()));
			}
			
			novoItem();
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}

	@Override
	public void removeItem() {
		funcionario.removeTelefone(telefoneSelecionado);
		
		FacesUtil.addSuccessMessage(this.getMensagemDeExclusaoDeItem(telefoneSelecionado.getNumero()));
		
		novoItem();
	}

	@Override
	public void editaItem() {
		telefone = telefoneSelecionado;
	}

	@Override
	public void novoItem() {
		this.telefone = new Telefone();
		telefoneSelecionado = null;
	}

	public Long getParamCodigo() {
		return paramCodigo;
	}
	public void setParamCodigo(Long paramCodigo) {
		this.paramCodigo = paramCodigo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Telefone getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Telefone getTelefoneSelecionado() {
		return telefoneSelecionado;
	}
	public void setTelefoneSelecionado(Telefone telefoneSelecionado) {
		this.telefoneSelecionado = telefoneSelecionado;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public Operadora[] getOperadoras() {
		return operadoras;
	}
}
