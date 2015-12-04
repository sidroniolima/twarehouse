/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.EstoqueDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.model.consulta.ProdutoEmReposicao;
import twarehouse.model.consulta.SaldoPorAlmoxarifado;
import twarehouse.model.estoque.Ajuste;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.model.estoque.ItemAjuste;
import twarehouse.model.estoque.ItensMovimentavies;
import twarehouse.model.estoque.Movimento;
import twarehouse.model.estoque.MovimentoBuilder;
import twarehouse.model.estoque.OrigemMovimento;
import twarehouse.model.estoque.TipoMovimento;
import twarehouse.service.AlmoxarifadoService;
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
	
	@Inject
	private AlmoxarifadoService almService;
	
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
		
		for (ItemAjuste item : ajusteDeEstoque.getMovimentacao()) {
			
			item.ajustaQuantidade();
			
			if (ajusteDeEstoque.getTipo().equals(TipoMovimento.ENTRADA)) {
				
				this.entrada(ajusteDeEstoque.getAlmOrigem(), item.getProduto(), item.getQtd(), ajusteDeEstoque.getOrigem());
			
			} else {
				
				if (ajusteDeEstoque.getTipo().equals(TipoMovimento.SAIDA)) {
					
					this.saida(ajusteDeEstoque.getAlmOrigem(), item.getProduto(), item.getQtd(), ajusteDeEstoque.getOrigem());
				
				} else {
					
					this.transferencia(ajusteDeEstoque.getAlmOrigem(), ajusteDeEstoque.getAlmDestino(), 
							item.getProduto(), item.getQtd(), ajusteDeEstoque.getOrigem());
				}
			}
		}
		
	}

	/* Saldo das movimentaçõe por almoxarifado do produto.
	 * @see twarehouse.service.EstoqueService#saldoDoProdutoEmAlmoxarifados(java.lang.Long)
	 */
	@Override
	public List<SaldoPorAlmoxarifado> saldoDoProdutoEmAlmoxarifados(Long produtoCodigo) {
		return estoqueDAO.saldoDoProdutoEmAlmoxarifados(produtoCodigo);
	}

	@Override
	public List<ProdutoEmReposicao> listaProdutosEsgotados(int firstResult, int results) {
		return estoqueDAO.listarProdutosEsgotados(firstResult, results);
	}

	@Override
	public List<ProdutoEmReposicao> listaProdutosEmReposicao(int firstResult, int results) {
		return estoqueDAO.listarProdutosEmReposicao(firstResult, results);
	}
	
	/* Realiza a compra, i.e, movimento dos itens.
	 * @see twarehouse.service.EstoqueService#realizaCompra(java.util.List)
	 */
	@Override
	public void realizaCompra(List<? extends ItensMovimentavies> itens) throws RegraDeNegocioException {
		
		Almoxarifado alm = almService.buscaAlmoxarifadoPrincipal();
		
		for (ItensMovimentavies item : itens) {
			
			this.entrada(alm, item.getProduto(), item.getQtd(), OrigemMovimento.COMPRA);
		}
	}
	
	/* Estorna a movimentação da compra.
	 * @see twarehouse.service.EstoqueService#estornaCompra(java.util.List)
	 */
	@Override
	public void estornaCompra(List<? extends ItensMovimentavies> itens) throws RegraDeNegocioException {
		
		Almoxarifado alm = almService.buscaAlmoxarifadoPrincipal();
		
		for (ItensMovimentavies item : itens) {
			
			this.saida(alm, item.getProduto(), item.getQtd(), OrigemMovimento.COMPRA);
		}
	}

	@Override
	public Long qtdDeProdutosEsgotados() {
		return null;
	}

	@Override
	public Long qtdDeProdutosEmReposicao() {
		return null;
	}

}
