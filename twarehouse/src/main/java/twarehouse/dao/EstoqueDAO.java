/**
 * 
 */
package twarehouse.dao;

import java.math.BigInteger;
import java.util.List;

import twarehouse.model.Produto;
import twarehouse.model.consulta.ProdutoEmReposicao;
import twarehouse.model.consulta.SaldoPorAlmoxarifado;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.model.estoque.Movimento;

/**
 * Interface da camada DAO para a Entidade Movimento.
 * 
 * @author Sidronio
 * 09/11/2015
 */
public interface EstoqueDAO extends GenericDAO<Movimento, Long>{

	/**
	 * Retorna a movimentação de entrada do produto por almoxarifado.
	 * 
	 * @param produto
	 * @param almoxarifado
	 * @return
	 */
	public List<Movimento> movimentosDeEntradaDoProdutoPorAlmoxarifado(Produto produto, Almoxarifado almoxarifado);
	
	
	/**
	 * Retorna a movimentação de saída do produto por almoxarifado.
	 * 
	 * @param produto
	 * @param almoxarifado
	 * @return
	 */
	public List<Movimento> movimentosDeSaidaDoProdutoPorAlmoxarifado(Produto produto, Almoxarifado almoxarifado);
	
	/**
	 * Retorna o saldo do produto agrupado pelos almoxarifados.
	 * 
	 * @param produtoCodigo
	 * @return
	 */
	public List<SaldoPorAlmoxarifado> saldoDoProdutoEmAlmoxarifados(Long produtoCodigo);
	
	/**
	 * Lista os registros dos produtos com quantidade zerada 
	 * de forma paginada. 
	 * 
	 * @param firstResult
	 * @param results
	 * @return
	 */
	public List<ProdutoEmReposicao> listarProdutosEsgotados(int firstResult, int results);
	
	/**
	 * Lista os registros dos produtos com quantidade de  
	 * reposição atingida mas com saldo positivo de forma paginada. 
	 * 
	 * @param firstResult
	 * @param results
	 * @return
	 */
	public List<ProdutoEmReposicao> listarProdutosEmReposicao(int firstResult, int results);
	
	/**
	 * Retorna a quantidade de produtos esgotados, isto é, 
	 * com saldo de movimentação igual ou menor que zero.
	 * 
	 * @return
	 */
	public BigInteger qtdDeProdutosEsgotados();
	
	/**
	 * Retorna a quantidade de produtos que atingiram o 
	 * limite de reposição.
	 * 
	 * @return
	 */
	public BigInteger qtdDeProdutosEmReposicao();
	
}
