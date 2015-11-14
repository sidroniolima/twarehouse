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
import twarehouse.model.Funcionario;
import twarehouse.service.impl.FuncionarioService;
import twarehouse.util.FacesUtil;
import twarehouse.util.Paginator;

/**
 * @author Sidronio
 *
 */
@Named
@ViewScoped
public class PesquisaFuncionarioBean extends PesquisaSingle implements Serializable {

	private static final long serialVersionUID = 5472550129749224407L;

	@Inject
	private FuncionarioService funcionarioService;
	
	private Funcionario funcionarioSelecionado;

	private List<Funcionario> funcionarios;
	
	private Paginator paginator;
	
	@Override
	@PostConstruct
	public void init() {
		paginator = new Paginator(10);
		
		if (null == funcionarios) {
			this.listarComPaginacao();
		}
	}

	@Override
	public void excluir() {
		try {
			
			funcionarioService.exclui(funcionarioSelecionado);
			
			FacesUtil.addSuccessMessage(this.getMensagemDeExclusaoOk(funcionarioSelecionado.getNome()));
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(this.getMensagemDeErroDeExclusao(funcionarioSelecionado.getNome(), e.getMessage()));
		}
	}

	@Override
	public void listarComPaginacao() {
		funcionarios = funcionarioService.listaTodosComPaginacao(paginator.getFirstResult(), paginator.getQtdPorPagina());
	}

	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("Funcionário %s excluído com sucesso.", registro);
	}

	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		return String.format("Não foi possível excluir o funcionário %s. %s", registro, msgError);
	}

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}
	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public Paginator getPaginator() {
		return paginator;
	}
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
}
