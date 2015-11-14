/**
 * 
 */
package twarehouse.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import twarehouse.model.CupomFiscal;
import twarehouse.model.Entrada;
import twarehouse.model.Fornecedor;
import twarehouse.model.Produto;
import twarehouse.model.builder.EntradaBuilder;

/**
 * @author Sidronio
 *
 */
public class EntradaTest {
	
	Entrada entrada;
	Fornecedor fornecedor;
	
	CupomFiscal cupomFiscal;

	@Before
	public void setUp() throws Exception {
		
		cupomFiscal = new CupomFiscal();
		fornecedor = new Fornecedor();
		
		entrada = new EntradaBuilder()
				.comDocumento(cupomFiscal)
				.doFornecedor(fornecedor)
				.comProduto(new Produto("Osso suíno"), new BigDecimal(2), new BigDecimal(6.5))
				.comProduto(new Produto("Biscoito canino"), new BigDecimal(2), new BigDecimal(2.5))
				.comProduto(new Produto("Ração 15Kg Golden"), new BigDecimal(1), new BigDecimal(115.00))
				.cria();
	}

	@Test
	public void deveRetornarAQuantideDeItens() {
		
		assertEquals(new BigDecimal(5).doubleValue(), entrada.qtdDeItens().doubleValue(), 0.01);
	}

	@Test
	public void deveRetornarOValorDosItens() {
		assertEquals(new BigDecimal(133).doubleValue(), entrada.valorDosItens().doubleValue(), 0.01);
	}
	
}
