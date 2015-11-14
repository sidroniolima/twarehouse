/**
 * 
 */
package twarehouse.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import twarehouse.model.Produto;
import twarehouse.model.Unidade;
import twarehouse.model.Unidades;

/**
 * @author Sidronio
 *
 * Cenário: 1 Kg do produto equivale a 100 centímetros 
 *
 */
public class UnidadesTest {

	Unidade unidadeKg;
	Unidade unidadeCm;
	
	Produto produtoAcabado;
	
	Unidades unidades;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		unidadeKg = new Unidade(new Long(1), "kilograma", "Kg");
		unidadeCm = new Unidade(new Long(1), "Centímetro", "Cm");
		
		produtoAcabado = new Produto();
		
		//1 Kg equivale a 50 centímetros
		unidades = new Unidades(produtoAcabado, unidadeKg, unidadeCm, new BigDecimal(50));
		produtoAcabado.setUnidades(unidades);
	}

	@Test
	public void deveCalcularDaUnidadeEntradaParaSaida() {
		
		BigDecimal qtdDoMovimento = new BigDecimal(100);
		
		BigDecimal unidadeCmEquivalente = produtoAcabado.getUnidades().converteDeEntradaParaSaida(qtdDoMovimento);
		
		assertEquals(new BigDecimal(5000).setScale(2), unidadeCmEquivalente);
	}
	
	@Test
	public void deveConverterDaSaidaParaEntrada() {
		
		BigDecimal qtdDoMovimento = new BigDecimal(100);
		
		BigDecimal unidadeKgEquivalente = produtoAcabado.getUnidades().converteDeSaidaParaEntrada(qtdDoMovimento);
		
		assertEquals(new BigDecimal(2).setScale(2), unidadeKgEquivalente);
	}

}
