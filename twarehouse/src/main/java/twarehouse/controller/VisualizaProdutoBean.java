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

import twarehouse.model.Produto;
import twarehouse.model.consulta.SaldoPorAlmoxarifado;
import twarehouse.service.EstoqueService;
import twarehouse.service.impl.ProdutoService;

/**
 * Controller das informações do produto e saldo em 
 * estoque para visualização.
 * 
 * @author Sidronio
 * 16/11/2015
 */
@Named
@ViewScoped
public class VisualizaProdutoBean implements Serializable{

	private static final long serialVersionUID = -7824697536205702687L;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private EstoqueService estoqueService;	
	
	@Inject @Param
	private Long paramCodigo;
	
	private Produto produto;
	List<SaldoPorAlmoxarifado> saldos;
	
	@PostConstruct
	public void init() {
		
		produto = produtoService.buscaPeloCodigoComSubgrupoEUnidades(paramCodigo);
		saldos = estoqueService.saldoDoProdutoEmAlmoxarifados(paramCodigo);
	}

	public Produto getProduto() {
		return produto;
	}

	public List<SaldoPorAlmoxarifado> getSaldos() {
		return saldos;
	}

}
