/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.EstoqueDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.molde.estoque.Ajuste;
import twarehouse.molde.estoque.Almoxarifado;
import twarehouse.molde.estoque.Movimento;
import twarehouse.molde.estoque.MovimentoBuilder;
import twarehouse.molde.estoque.OrigemMovimento;
import twarehouse.molde.estoque.TipoMovimento;
import twarehouse.service.EstoqueService;

/**
 * Implementa a interface EstoqueService da camada Service, responsável 
 * pelas regras do modelo MVC e requisições à camada DAO.
 * 
 * @author Sidronio
 * 09/11/2015
 */
@Stateless
public class EstoqueServiceImpl implements EstoqueService, Serializable {

	private static final long serialVersionUID = -1510131021552716870L;

	@Inject
	private EstoqueDAO estoqueDAO;
	
	public void saida(
			Almoxarifado almoxarifadoOrigem, 
			Produto produto, 
			BigDecimal qtd, 
			OrigemMovimento origem) throws RegraDeNegocioException {
		
		Movimento movimentoDeSaida = new MovimentoBuilder()
				.saida()
				.doAlmoxarifado(almoxarifadoOrigem)
				.doProduto(produto)
				.comQuantidade(qtd)
				.daOrigem(origem)
				.cria();
		
		this.salva(movimentoDeSaida);
	}
	
	public void entrada(
			Almoxarifado almoxarifadoOrigem, 
			Produto produto, 
			BigDecimal qtd, 
			OrigemMovimento origem) throws RegraDeNegocioException {
		
		Movimento movimentoDeEntrada = new MovimentoBuilder()
				.entrada()
				.doAlmoxarifado(almoxarifadoOrigem)
				.doProduto(produto)
				.comQuantidade(qtd)
				.daOrigem(origem)
				.cria();
		
		this.salva(movimentoDeEntrada);
		
	}
	
	public void transferencia(
			Almoxarifado almoxarifadoOrigem, 
			Almoxarifado almoxarifadoDestino, 
			Produto produto, 
			BigDecimal qtd, 
			OrigemMovimento origem) throws RegraDeNegocioException {
		
		Movimento movimentoDeSaida = new MovimentoBuilder()
				.saida()
				.doAlmoxarifado(almoxarifadoOrigem)
				.doProduto(produto)
				.comQuantidade(qtd)
				.daOrigem(origem)
				.cria();
		
		this.salva(movimentoDeSaida);
		
		Movimento movimentoDeEntrada = new MovimentoBuilder()
				.entrada()
				.doAlmoxarifado(almoxarifadoDestino)
				.doProduto(produto)
				.comQuantidade(qtd)
				.daOrigem(origem)
				.cria();
		
		this.salva(movimentoDeEntrada);
	}

	/* Salva o movimento.
	 * @see twarehouse.service.EstoqueService#salva(twarehouse.molde.estoque.Movimento)
	 */
	@Override
	public void salva(Movimento movimento) throws RegraDeNegocioException {
		
		this.valida(movimento);
		this.estoqueDAO.salvar(movimento);
	}

	/* Valida o movimento.
	 * @see twarehouse.service.EstoqueService#valida(twarehouse.molde.estoque.Movimento)
	 */
	@Override
	public void valida(Movimento movimento) throws RegraDeNegocioException {
		
		if (null == movimento.getAlmoxarifado()) {
			throw new RegraDeNegocioException("Um movimento deve ser de algum almoxarifado.");
		}
		
		if(null == movimento.getProduto()) {
			throw new RegraDeNegocioException("Deve ser definido qual o produto da movimentação.");
		}
		
		if(null == movimento.getQtd() || movimento.getQtd().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RegraDeNegocioException("A quantidade precisa ser maior que zero.");
		}		
		
		if (null == movimento.getTipoMovimento()) {
			throw new RegraDeNegocioException("O movimento deve ser de entrada, saída ou transferência.");
		}
		
		if (null == movimento.getOrigem()) {
			throw new RegraDeNegocioException("A origem da movimentação deve ser informada.");
		}		
	}

	/* Ajuste de estoque.
	 * @see twarehouse.service.EstoqueService#ajusta(java.util.List)
	 */
	@Override
	public void ajusta(Ajuste ajusteDeEstoque) throws RegraDeNegocioException {
		
		for(Entry<Produto, BigDecimal> entry : ajusteDeEstoque.getMovimentacao().entrySet()) {
			Produto produto = entry.getKey();
			BigDecimal qtd = entry.getValue();
			
			if (ajusteDeEstoque.getTipo().equals(TipoMovimento.ENTRADA)) {
				
				this.entrada(ajusteDeEstoque.getAlmOrigem(), produto, qtd, ajusteDeEstoque.getOrigem());
			
			} else {
				
				if (ajusteDeEstoque.getTipo().equals(TipoMovimento.SAIDA)) {
					
					this.saida(ajusteDeEstoque.getAlmOrigem(), produto, qtd, ajusteDeEstoque.getOrigem());
				
				} else {
					
					this.transferencia(ajusteDeEstoque.getAlmOrigem(), ajusteDeEstoque.getAlmDestino(), 
							produto, qtd, ajusteDeEstoque.getOrigem());
				}
			}
		}
		
	}
}
