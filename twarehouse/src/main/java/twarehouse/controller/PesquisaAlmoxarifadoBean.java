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
import twarehouse.molde.estoque.Almoxarifado;
import twarehouse.service.AlmoxarifadoService;
import twarehouse.util.FacesUtil;
import twarehouse.util.IConstants;
import twarehouse.util.Paginator;

/**
 * Classe de implementação da camada controller da entidade 
 * Almoxarifado responsável pelo pesquisa e exclusão.
 * 
 * @author Sidronio
 * 09/11/2015
 */
@Named
@ViewScoped
public class PesquisaAlmoxarifadoBean extends PesquisaSingle implements Serializable {

	private static final long serialVersionUID = -8739448073288375849L;

	@Inject
	private AlmoxarifadoService almoxarifadoService;
	
	private Almoxarifado almoxarifadoSelecionado;
	private List<Almoxarifado> almoxarifados;
	
	private Paginator paginator;
	
	/* Inicia as variáveis.
	 * @see twarehouse.controller.PesquisaSingle#init()
	 */
	@PostConstruct
	@Override
	public void init() {
		
		paginator = new Paginator(IConstants.QTD_PADRAO_POR_PAGINA);
		listarComPaginacao();
	}

	/* Exclui uma instância.
	 * @see twarehouse.controller.PesquisaSingle#excluir()
	 */
	@Override
	public void excluir() {
		
		try {
			almoxarifadoService.exclui(almoxarifadoSelecionado.getCodigo());
			
			almoxarifados.remove(almoxarifadoSelecionado);
			
			FacesUtil.addSuccessMessage(getMensagemDeExclusaoOk(almoxarifadoSelecionado.getDescricao()));
			
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(getMensagemDeErroDeExclusao(almoxarifadoSelecionado.getDescricao(), e.getMessage()));
		}
		
	}

	/* Lista os registros de forma paginada.
	 * @see twarehouse.controller.PesquisaSingle#listarComPaginacao()
	 */
	@Override
	public void listarComPaginacao() {
		almoxarifados = almoxarifadoService.listaComPaginacao(
				paginator,
				Arrays.asList("descricao"),
				null,
				null);
	}

	/* Mensagem de exclusão
	 * @see twarehouse.controller.PesquisaSingle#getMensagemDeExclusaoOk(java.lang.String)
	 */
	@Override
	public String getMensagemDeExclusaoOk(String registro) {
		return String.format("Almoxarifado %s excluído com sucesso.", registro);
	}

	/* Mensagem de erro de exclusçao
	 * @see twarehouse.controller.PesquisaSingle#getMensagemDeErroDeExclusao(java.lang.String, java.lang.String)
	 */
	@Override
	public String getMensagemDeErroDeExclusao(String registro, String msgError) {
		return String.format("Não foi possível excluir o almoxarifado %s. %s", registro, msgError);
	}

	public Almoxarifado getAlmoxarifadoSelecionado() {
		return almoxarifadoSelecionado;
	}
	public void setAlmoxarifadoSelecionado(Almoxarifado almoxarifadoSelecionado) {
		this.almoxarifadoSelecionado = almoxarifadoSelecionado;
	}

	public Paginator getPaginator() {
		return paginator;
	}
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}
	
	public List<Almoxarifado> getAlmoxarifados() {
		return almoxarifados;
	}
}
