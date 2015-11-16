/**
 * 
 */
package twarehouse.molde.estoque;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.model.estoque.Ajuste;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.model.estoque.TipoMovimento;

/**
 * Teste da classe de negócios Ajuste.
 * 
 * @author Sidronio
 * 09/11/2015
 */
public class AjusteTest {

	private Ajuste ajuste;
	private Produto produto1;
	private Produto produto2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		produto1 = new Produto(1L, "Produto 1", BigDecimal.ONE);
		produto2 = new Produto(2L, "Produto 2", BigDecimal.TEN);
		
		ajuste = new Ajuste();	
	}

	
	@Test(expected=RegraDeNegocioException.class)
	public void deveLancaExcecaoPorNaoHaverTipo() throws RegraDeNegocioException {
		
		ajuste.adicionaMovimento(produto1, new BigDecimal(10));
		ajuste.adicionaMovimento(produto2, new BigDecimal(5));
		
		ajuste.valida();
	}
	
	@Test
	public void deveValidarUmAjusteDeEntradaComDuasMovimentacoes() throws RegraDeNegocioException {
		
		ajuste.setTipo(TipoMovimento.ENTRADA);
		ajuste.setAlmOrigem(new Almoxarifado(1L, "Principal"));
		
		ajuste.adicionaMovimento(produto1, new BigDecimal(10));
		ajuste.adicionaMovimento(produto2, new BigDecimal(5));
		
		ajuste.valida();
	}
	
	@Test
	public void deveRemoverUmaMovimentacao() throws RegraDeNegocioException {
		
		ajuste.setTipo(TipoMovimento.ENTRADA);
		ajuste.setAlmOrigem(new Almoxarifado(1L, "Principal"));
		
		ajuste.adicionaMovimento(produto1, new BigDecimal(10));
		ajuste.adicionaMovimento(produto2, new BigDecimal(5));
		
		ajuste.valida();
		
		assertEquals(2, ajuste.getTamanho());
		
		ajuste.removeMovimento(produto2);
		
		assertEquals(1, ajuste.getTamanho());
	}
	
}
