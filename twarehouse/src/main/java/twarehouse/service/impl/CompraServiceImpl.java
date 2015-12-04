/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.CompraDAO;
import twarehouse.dao.GenericDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Compra;
import twarehouse.model.consulta.FiltroEntrada;
import twarehouse.service.CompraService;
import twarehouse.service.EstoqueService;
import twarehouse.service.SimpleServiceLayerImpl;
import twarehouse.util.Paginator;

/**
 * Implementação da camada Service da entidade 
 * Compra.
 * 
 * @author Sidronio
 * 26/11/2015
 */
@Stateless
public class CompraServiceImpl extends SimpleServiceLayerImpl<Compra, Long> implements CompraService, Serializable {

	private static final long serialVersionUID = 8830512301515474398L;

	@Inject
	private CompraDAO compraDAO;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Override
	public void salva(Compra entidade) throws RegraDeNegocioException {
		super.salva(entidade);
		
		if (this.isEdicao(entidade)) {
			estoqueService.estornaCompra(entidade.getEstadoAnterior().getEstado().getItens());
		} 
		
		estoqueService.realizaCompra(entidade.getItens());
		
	}
	
	/* Exclui a compra e gera o estorno da movimentação.
	 * @see twarehouse.service.SimpleServiceLayerImpl#exclui(java.io.Serializable)
	 */
	@Override
	public void exclui(Long id) throws RegraDeNegocioException {
		
		Compra compraComItens = 
				this.compraDAO.buscarPeloCodigoComRelacionamento(id, Arrays.asList("itens"));
		
		super.exclui(id);
		
		this.estoqueService.estornaCompra(compraComItens.getItens());
		
	}
	
	@Override
	public void validaInsercao(Compra compra) throws RegraDeNegocioException {
		compra.valida();
	}

	@Override
	public void validaExclusao(Compra compra) throws RegraDeNegocioException {
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GenericDAO getDAO() {
		return this.compraDAO;
	}

	@Override
	public Compra buscaPeloCodigoCompleta(Long codigo) {
		
		return this.compraDAO.buscarPeloCodigoComRelacionamento(
				codigo,
				Arrays.asList(
						"documento", 
						"fornecedor",
						"fornecedor.pessoa",
						"itens",
						"itens.produto"));
	}

	@Override
	public List<Compra> filtraPelaPesquisa(FiltroEntrada filtro, Paginator paginator) {
		return this.compraDAO.filtrarPeloModoEspecifico(
				filtro, 
				paginator.getFirstResult(), 
				paginator.getQtdPorPagina());
	}
	
	/* (non-Javadoc)
	 * @see twarehouse.service.CompraService#isEdicao(twarehouse.model.Compra)
	 */
	@Override
	public boolean isEdicao(Compra entidade) {
		return null != entidade.getCodigo();
	}
	
	/* (non-Javadoc)
	 * @see twarehouse.service.CompraService#buscaPeloCodigoComItens(java.lang.Long)
	 */
	@Override
	public Compra buscaPeloCodigoComItens(Long codigo) {
		
		return compraDAO.buscarPeloCodigoComRelacionamento(
				codigo, 
				Arrays.asList("itens"));
	}

}
