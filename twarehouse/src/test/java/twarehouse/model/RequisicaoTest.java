/**
 * 
 */
package twarehouse.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import twarehouse.model.builder.RequisicaoBuilder;
import twarehouse.model.exception.RequisicaoException;

public class RequisicaoTest {

	private Requisicao requisicao;
	private Produto produtoDevolvido;
	
	@Before
	public void setUp() throws Exception {
		
		requisicao = new RequisicaoBuilder()
						.doFuncionario(new Funcionario())
						.naData(LocalDate.now())
						.comProduto(new Produto(1L, "Osso suíno", new BigDecimal(6.2)), new BigDecimal(4))
						.comProduto(new Produto(2L, "Ração 15Kg", new BigDecimal(133)), new BigDecimal(1))
						.cria();
		
		produtoDevolvido = new Produto(1L, "Osso suíno", new BigDecimal(6.2));
		
	}

	@Test
	public void deveRetornarQtdEntregue() {
		
		assertEquals(new BigDecimal(5).doubleValue(), requisicao.qtdItensEntregues().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarValorEntregue() {
		
		assertEquals(new BigDecimal(157.8).doubleValue(), requisicao.valorEntregue().doubleValue(), 0.01);
	}
	
	@Test(expected=RequisicaoException.class)
	public void deveLancarExcecaoPorNaoAcharProdutoDevolvido() throws RequisicaoException {
		
		Produto produto = new Produto(4L, "Osso suíno", new BigDecimal(6.2));
		
		requisicao.devolveQtdDoProduto(produto, new BigDecimal(0));
	}
	
	@Test(expected=RequisicaoException.class)
	public void deveLancarExcecaoPorTentarDevolverUmaQtdMaior() throws RequisicaoException {
		
		requisicao.devolveQtdDoProduto(produtoDevolvido, new BigDecimal(20));
	}
	
	@Test
	public void deveRetornarQtdDevolvida() throws RequisicaoException {
		
		requisicao.devolveQtdDoProduto(produtoDevolvido, new BigDecimal(2));
		
		assertEquals(new BigDecimal(2).doubleValue(), requisicao.qtdItensDevolvidos().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarValorDevolvido() throws RequisicaoException {
		requisicao.devolveQtdDoProduto(produtoDevolvido, new BigDecimal(2));
		assertEquals(new BigDecimal(12.4).doubleValue(), requisicao.valorDevolvido().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarQtdUtilizada() throws RequisicaoException {
		
		requisicao.devolveQtdDoProduto(produtoDevolvido, new BigDecimal(1));
		assertEquals(new BigDecimal(4).doubleValue(), requisicao.qtdItensUtilizados().doubleValue(), 0.01);
	}
	
	@Test
	public void deveRetornarValorUtilizado() throws RequisicaoException {
		requisicao.devolveQtdDoProduto(produtoDevolvido, new BigDecimal(3));
		assertEquals(new BigDecimal(139.2).doubleValue(), requisicao.valorUtilizado().doubleValue(), 0.01);
	}

}
