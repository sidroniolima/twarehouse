/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Unidade;
import twarehouse.service.UnidadeService;
import twarehouse.util.FacesUtil;
import twarehouse.util.IConstants;
import twarehouse.util.Paginator;

/**
 * Controller responsável pela listagem das unidades cadastradas 
 * e a exclusão.
 * 
 * @author Sidronio
 * 11/05/2015
 */
@Named
@ViewScoped
public class PesquisaUnidadeBean extends PesquisaSingle implements Serializable{

	private static final long serialVersionUID = -958217119451658494L;

	@Inject
	private UnidadeService unidadeService;
	
	private List<Unidade> unidades;
	private Unidade unidadeSelecionada;
	
	private Paginator paginator;
	
	@PostConstruct
	@Override
	public void init() {
		
		paginator = new Paginator(IConstants.QTD_PADRAO_POR_PAGINA);
		
		this.listarComPaginacao();
	}

	@Override
	public void excluir() {
		
		try {
			
			unidadeService.exclui(unidadeSelecionada.getCodigo());
			
			unidades.remove(unidadeSelecionada);
			
			FacesUtil.addSuccessMessage(getMensagemDeExclusaoOk(unidadeSelecionada.getDescricao()));
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(getMensagemDeErroDeExclusao(unidadeSelecionada.getDescricao(), e.getMessage()));
		}
	}

	@Override
	public void listarComPaginacao() {
		unidades = unidadeService.
				listaComPaginacao(paginator, Arrays.asList("codigo"), null, null);
	}

	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("Unidade %s excluída com sucesso.", registro);
	}

	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		return String.format("Não foi possível excluir a unidade %s. %s", registro, msgError);
	}

	public Unidade getUnidadeSelecionada() {
		return unidadeSelecionada;
	}
	public void setUnidadeSelecionada(Unidade unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
	}

	public Paginator getPaginator() {
		return paginator;
	}
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}
}
