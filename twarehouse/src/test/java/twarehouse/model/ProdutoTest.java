/**
 * 
 */
package twarehouse.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

/**
 * Teste unitário da entidade Produto.
 * 
 * @author Sidronio
 *
 */
public class ProdutoTest {

	private Produto produto;
	private Unidade entrada;
	private Unidade saida;
	private BigDecimal razao;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		produto = new Produto(1L, "Descrição do produto", new BigDecimal(17.2));
		entrada = new Unidade(1L, "Kilograma", "kg");
		saida = new Unidade(1L, "Grama", "gr");
		
		razao = new BigDecimal(1000);
	}

	@Test
	public void deveAdicionarUnidades() {
		
		produto.adicionaUnidades(entrada, saida, razao);
		
		assertEquals(produto.getUnidades().getEntrada(), entrada);
		assertEquals(produto.getUnidades().getSaida(), saida);
	}
	
	@Test
	public void deveConverterMedidas() {
		produto.adicionaUnidades(entrada, saida, razao);
		
		assertEquals(
				new BigDecimal(10000).setScale(2, RoundingMode.HALF_UP), 
				produto.getUnidades().converteDeEntradaParaSaida(new BigDecimal(10)));
		
		assertEquals(
				new BigDecimal(0.10).setScale(2, RoundingMode.HALF_UP), 
				produto.getUnidades().converteDeSaidaParaEntrada(new BigDecimal(100)));
	}

}
