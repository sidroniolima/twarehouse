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
import twarehouse.factory.PessoaFactory;
import twarehouse.model.Fornecedor;
import twarehouse.model.Operadora;
import twarehouse.model.Telefone;
import twarehouse.model.TipoPessoa;
import twarehouse.service.impl.FornecedorService;
import twarehouse.util.FacesUtil;

/**
 * Bean de Cadastro da Entidade Fornecedor representando a camada View
 * do modelo MVC.
 * 
 * @author Sidronio
 * 04/11/2015
 * 
 */
@Named
@ViewScoped
public class CadastroFornecedorBean extends CadastroMasterDetail implements Serializable {

	private static final long serialVersionUID = -5932223554994472591L;

	@Inject
	private FornecedorService fornecedorService;
	
	@Inject @Param
	private Long paramCodigo;
	
	private Fornecedor fornecedor;
	
	private Telefone telefone;
	private Telefone telefoneSelecionado;
	
	private Operadora[] operadoras;

	@Override
	void novoRegistro() {
		fornecedor = new Fornecedor();
		novoItem();
	}

	@Override
	@PostConstruct
	void init() {
		
		if (isEdicao()) {
			
			fornecedor = fornecedorService.buscaPeloCodigoComPessoaETelefones(paramCodigo);
			novoItem();
		} else {
			novoRegistro();
		}
		
		operadoras = Operadora.values();
	}

	/**
	 * Atribui um objeto Pessoa ao Fornecedor, utilizando 
	 * uma fábrica de objetos que retorna o tipo, física ou jurídica.s
	 * 
	 * @param tipo
	 */
	public void criaPessoa(String tipo) {
		fornecedor.setPessoa(PessoaFactory.criaPessoa(TipoPessoa.valueOf(tipo)));
	}
	
	@Override
	public void salvar() {
		fornecedorService.salva(fornecedor);
		
		if (isEdicao()) {
			FacesUtil.addSuccessMessage(this.getMensagemDeAlteracao(fornecedor.getPessoa().getNome()));
		} else {
			FacesUtil.addSuccessMessage(this.getMensagemDeInclusao(fornecedor.getPessoa().getNome()));
		}
		
		novoRegistro();
	}

	@Override
	boolean isEdicao() {
		return null != paramCodigo;
	}

	@Override
	String getMensagemDeInclusao(String registro) {
		return String.format("Fornecedor %s incluído com sucesso.", registro);
	}

	@Override
	String getMensagemDeAlteracao(String registro) {
		return String.format("Fornecedor %s alterado com sucesso.", registro);
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
				fornecedor.adicionaTelefone(telefone);
				
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
		fornecedor.removeTelefone(telefoneSelecionado);
		
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
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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

	public Operadora[] getOperadoras() {
		return operadoras;
	}
	
}
