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
import twarehouse.model.Fornecedor;
import twarehouse.service.impl.FornecedorService;
import twarehouse.util.FacesUtil;
import twarehouse.util.IConstants;
import twarehouse.util.Paginator;

/**
 * Classe da camada Controller para a listagem dos registros 
 * da Entidade Fornecedor.
 * 
 * @author Sidronio
 * 06/11/2015
 */
@Named
@ViewScoped
public class PesquisaFornecedorBean extends PesquisaSingle implements Serializable{

	private static final long serialVersionUID = -5916012336240256916L;

	@Inject
	private FornecedorService fornecedorService;
	
	private Fornecedor fornecedorSelecionado;
	
	private List<Fornecedor> fornecedores;
	private Paginator paginator;
	
	private Filtro filtro;
	
	/**
	 * Classe auxiliar utilizada para filtrar os Fornecedores.
	 * 
	 * @author Sidronio
	 * 06/11/2025
	 */
	public class Filtro {
		
		private String nome;
		private String documento;
		private String numeroTelefone;
		
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		public String getDocumento() {
			return documento;
		}
		public void setDocumento(String documento) {
			this.documento = documento;
		}
		
		public String getNumeroTelefone() {
			return numeroTelefone;
		}
		public void setNumeroTelefone(String numeroTelefone) {
			this.numeroTelefone = numeroTelefone;
		}
		
	}
	
	@Override
	@PostConstruct
	public void init() {
		
		paginator = new Paginator(IConstants.QTD_FORNECEDORES_POR_PAGINA);
		
		if (null == fornecedores) {
			this.listarComPaginacao();
		}
		
		filtro = new Filtro();
	}

	@Override
	public void excluir() {
		try {
			fornecedorService.exclui(fornecedorSelecionado);
			
			fornecedores.remove(fornecedorSelecionado);
			
			FacesUtil.addSuccessMessage(this.getMensagemDeExclusaoOk(fornecedorSelecionado.getPessoa().getNome()));
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addSuccessMessage(
					this.getMensagemDeErroDeExclusao(fornecedorSelecionado.getPessoa().getNome(), e.getMessage()));
		}
		
	}
	
	/**
	 * Filtra os registros pelo documento (cpf ou cnpj), nome (nome ou 
	 * nome fantasia ou pelo número do telefone.
	 */
	public void filtrar(){
		List<Fornecedor> fornecedoresFiltrados = fornecedorService.filtra(filtro);
		
		if (null == fornecedoresFiltrados || fornecedoresFiltrados.size() == 0) {
			FacesUtil.addErrorMessage("Não foram achados registros para esse filtro.");
		} else {
			fornecedores = fornecedoresFiltrados;
			filtro = new Filtro();
		}
	}

	@Override
	public void listarComPaginacao() {
		fornecedores = fornecedorService.listaComPaginacao(paginator);
	}

	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("Fornecedor %s excluído com sucesso.", registro);
	}

	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		return String.format("Não foi possível exclui o Fornecedor %s. %s", registro, msgError);
	}

	public Fornecedor getFornecedorSelecionado() {
		return fornecedorSelecionado;
	}
	public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
		this.fornecedorSelecionado = fornecedorSelecionado;
	}

	public Paginator getPaginator() {
		return paginator;
	}
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public Filtro getFiltro() {
		return filtro;
	}
	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}
	
}
