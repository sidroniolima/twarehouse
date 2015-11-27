/**
 * 
 */
package twarehouse.molde.estoque;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.model.Unidade;
import twarehouse.model.Unidades;
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
	
	private Unidades unidades1;
	private Unidades unidades2;
	
	private Unidade kg;
	private Unidade grama;
	private Unidade caixa;
	private Unidade un;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		produto1 = new Produto(1L, "Produto 1", BigDecimal.ONE);
		produto2 = new Produto(2L, "Produto 2", BigDecimal.TEN);
		
		kg = new Unidade(1L, "Quilo", "Kg");
		grama = new Unidade(2L, "Grama", "g");
		caixa = new Unidade(3L, "Caixa", "Cx");
		un = new Unidade(4L, "Unidade", "Un");
		
		unidades1 = new Unidades(
				produto1, 
				kg, 
				grama, 
				new BigDecimal(1000));
		
		unidades2 = new Unidades(
				produto2, 
				caixa, 
				un, 
				new BigDecimal(10));
		
		ajuste = new Ajuste();	
	}

	
	@Test(expected=RegraDeNegocioException.class)
	public void deveLancaExcecaoPorNaoHaverTipo() throws RegraDeNegocioException {
		
		ajuste.adicionaMovimento(produto1, kg, new BigDecimal(10));
		ajuste.adicionaMovimento(produto2, grama, new BigDecimal(5));
		
		ajuste.valida();
	}
	
	@Test
	public void deveValidarUmAjusteDeEntradaComDuasMovimentacoes() throws RegraDeNegocioException {
		
		ajuste.setTipo(TipoMovimento.ENTRADA);
		ajuste.setAlmOrigem(new Almoxarifado(1L, "Principal"));
		
		ajuste.adicionaMovimento(produto1, kg, new BigDecimal(10));
		ajuste.adicionaMovimento(produto2, grama, new BigDecimal(5));
		
		ajuste.valida();
	}
	
	@Test
	public void deveRemoverUmaMovimentacao() throws RegraDeNegocioException {
		
		ajuste.setTipo(TipoMovimento.ENTRADA);
		ajuste.setAlmOrigem(new Almoxarifado(1L, "Principal"));
		
		ajuste.adicionaMovimento(produto1, kg, new BigDecimal(10));
		ajuste.adicionaMovimento(produto2, grama, new BigDecimal(5));
		
		ajuste.valida();
		
		assertEquals(2, ajuste.getTamanho());
		
		ajuste.removeItem(produto2);
		
		assertEquals(1, ajuste.getTamanho());
	}
	
	@Test(expected=RegraDeNegocioException.class)
	public void deveLancarExcecaoDeProdutoNaoLocalizado() throws RegraDeNegocioException {
		
		ajuste.setTipo(TipoMovimento.ENTRADA);
		ajuste.setAlmOrigem(new Almoxarifado(1L, "Principal"));
		
		ajuste.adicionaMovimento(produto1, kg, new BigDecimal(10));
		ajuste.adicionaMovimento(produto2, grama, new BigDecimal(5));
		
		ajuste.valida();
		
		ajuste.removeItem(new Produto("Produto inexistente."));
		
	}

	@Test
	public void deveIndicarQueEhNecessarioAConversaoEntreAsUnidades(){
		
		ajuste.setTipo(TipoMovimento.ENTRADA);
		ajuste.setAlmOrigem(new Almoxarifado(1L, "Principal"));
		
		
		try {
			
			ajuste.adicionaMovimento(produto1, grama, new BigDecimal(10));
			ajuste.adicionaMovimento(produto2, caixa, new BigDecimal(5));

		} catch (RegraDeNegocioException e) {
		
		}
		
		assertTrue(ajuste.getMovimentacao().get(0).ehNecessarioConverterQuantidade());
		assertFalse(ajuste.getMovimentacao().get(1).ehNecessarioConverterQuantidade());
	}
	
	@Test
	public void deveConverterAsUnidadesSeNecessario(){
		
		ajuste.setTipo(TipoMovimento.ENTRADA);
		ajuste.setAlmOrigem(new Almoxarifado(1L, "Principal"));
		
		
		try {
			
			ajuste.adicionaMovimento(produto1, grama, new BigDecimal(10));
			ajuste.adicionaMovimento(produto2, caixa, new BigDecimal(5));
			
			ajuste.adicionaMovimento(produto2, un, new BigDecimal(5));

		} catch (RegraDeNegocioException e) {
		
		}
		
		ajuste.getMovimentacao().get(0).ajustaQuantidade();
		ajuste.getMovimentacao().get(1).ajustaQuantidade();
		ajuste.getMovimentacao().get(2).ajustaQuantidade();
		
		assertEquals(ajuste.getMovimentacao().get(0).getQtd().doubleValue(), 0.01d, 0.01);
		assertEquals(ajuste.getMovimentacao().get(1).getQtd().doubleValue(), 5d, 0.01);
		
		assertEquals(ajuste.getMovimentacao().get(2).getQtd().doubleValue(), 0.5d, 0.01);
	}
	
}
