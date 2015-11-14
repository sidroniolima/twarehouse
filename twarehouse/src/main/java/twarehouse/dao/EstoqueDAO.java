/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Produto;
import twarehouse.model.consulta.SaldoEstoque;
import twarehouse.molde.estoque.Almoxarifado;
import twarehouse.molde.estoque.Movimento;

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
	public List<SaldoEstoque> saldoDoProdutoEmAlmoxarifados(Long produtoCodigo);
	
}
