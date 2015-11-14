/**
 * 
 */
package twarehouse.service;

import java.math.BigDecimal;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.molde.estoque.Ajuste;
import twarehouse.molde.estoque.Almoxarifado;
import twarehouse.molde.estoque.Movimento;
import twarehouse.molde.estoque.OrigemMovimento;

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
}
