/**
 * 
 */
package twarehouse.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import twarehouse.model.ItemRequisicao;
import twarehouse.model.Produto;

public class ItemRequisicaoTest {

	ItemRequisicao item;
	
	@Before
	public void setUp() throws Exception {
		
		item = new ItemRequisicao(
				null, 
				new Produto(1L, "Osso suíno", new BigDecimal(6.2)), new BigDecimal(5));
		
		item.setQtdDevolvida(BigDecimal.ONE);
	}

	@Test
	public void deveRetornarQtdEntregue() {
		
		assertEquals(new BigDecimal(5).doubleValue(), item.getQtdEntregue().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarValorEntregue() {
		
		assertEquals(new BigDecimal(31).doubleValue(), item.valorEntregue().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarQtdDevolvida() {
		
		assertEquals(new BigDecimal(1).doubleValue(), item.getQtdDevolvida().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarValorDevolvido() {
		
		assertEquals(new BigDecimal(6.2).doubleValue(), item.valorDevolvido().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarQtdUtilizada() {
		
		assertEquals(new BigDecimal(4).doubleValue(), item.qtdUtilizada().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarValorUtilizado() {
		
		assertEquals(new BigDecimal(24.8).doubleValue(), item.valorUtilizado().doubleValue(), 0.01);
	}

}
