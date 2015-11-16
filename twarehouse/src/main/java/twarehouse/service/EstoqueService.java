/**
 * 
 */
package twarehouse.service;

import java.math.BigDecimal;
import java.util.List;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.model.consulta.ProdutoEmReposicao;
import twarehouse.model.consulta.SaldoPorAlmoxarifado;
import twarehouse.model.estoque.Ajuste;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.model.estoque.Movimento;
import twarehouse.model.estoque.OrigemMovimento;

/**
 * Interface para abstração do Estoque.
 * 
 * @author Sidronio
 * 09/11/2015
 */
/**
 * @author Sidronio
 *
 */
public interface EstoqueService {

	/**
	 * Salva uma instância de movimento.
	 * 
	 * @param movimento
	 */
	public void salva(Movimento movimento) throws RegraDeNegocioException;
	
	/**
	 * Valida o movimento.
	 * 
	 * @param movimento
	 */
	public void valida(Movimento movimento) throws RegraDeNegocioException;
	
	/**
	 * Gera uma saída de um produto de um almoxarifado.
	 * 
	 * @param almoxarifadoOrigem
	 * @param produto
	 * @param qtd
	 * @param origem
	 */
	public void saida(
			Almoxarifado almoxarifadoOrigem, 
			Produto produto, 
			BigDecimal qtd, 
			OrigemMovimento origem) throws RegraDeNegocioException;
	
	/**
	 * Gera uma entrada do produto no almoxarifado especificado.
	 * 
	 * @param almoxarifadoOrigem
	 * @param produto
	 * @param qtd
	 * @param origem
	 */
	public void entrada(
			Almoxarifado almoxarifadoOrigem, 
			Produto produto, 
			BigDecimal qtd, 
			OrigemMovimento origem) throws RegraDeNegocioException;
	
	/**
	 * Transfere a quantidade de um produto entre dois almoxaifados 
	 * passados.
	 * 
	 * @param almoxarifadoOrigem
	 * @param almoxarifadoDestino
	 * @param produto
	 * @param qtd
	 * @param origem
	 */
	public void transferencia(
			Almoxarifado almoxarifadoOrigem, 
			Almoxarifado almoxarifadoDestino, 
			Produto produto, 
			BigDecimal qtd, 
			OrigemMovimento origem) throws RegraDeNegocioException;
	
	/**
	 * Ajuste de estoque manual com movimentações de entrada, saída ou 
	 * transferência.
	 * 
	 * @param movimentacao
	 */
	public void ajusta(Ajuste ajusteDeEstoque) throws RegraDeNegocioException;
	
	/**
	 * Retorna o saldo do produto por almoxarifado.
	 * 
	 * @param produtoCodigo Código do produto para cálculo de saldo.
	 * @return
	 */
	public List<SaldoPorAlmoxarifado> saldoDoProdutoEmAlmoxarifados(Long produtoCodigo);
	
	/**
	 * Lista produtos sem estoque, isto é, esgotados.
	 * 
	 * @return
	 */
	public List<ProdutoEmReposicao> listaProdutosEsgotados(int firstResult, int results);
	
	/**
	 * Lista produtos que atingiram o nível de reposição mas não estão 
	 * zerados.
	 * 
	 * @param firstResult
	 * @param results
	 * @return
	 */
	public List<ProdutoEmReposicao> listaProdutosEmReposicao(int firstResult, int results);
	
	/**
	 * Retorna a quantidade de produtos esgotados.
	 * 
	 * @return
	 */
	public Long qtdDeProdutosEsgotados();
	
	/**
	 * Retorna a quantidade de produtos que atingiram a quantidade de 
	 * reposição.
	 * 
	 * @return
	 */
	public Long qtdDeProdutosEmReposicao();
}
