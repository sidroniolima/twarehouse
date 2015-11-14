/**
 * 
 */
package twarehouse.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import twarehouse.model.CupomFiscal;
import twarehouse.model.NotaFiscalEletronica;
import twarehouse.model.TipoDocumentoEntrada;
import twarehouse.model.builder.DocumentoEntradaBuilder;

/**
 * @author Sidronio
 *
 */
public class DocumentoEntradaTest {

	private CupomFiscal cupomFiscal;
	private NotaFiscalEletronica nfe;
	
	@Before
	public void setUp() throws Exception {
		
		cupomFiscal = (CupomFiscal) new DocumentoEntradaBuilder()
						.cupomFiscal()
						.comDesconto(new BigDecimal(1.2))
						.comSubTotal(new BigDecimal(234.00))
						.naData(LocalDate.now())
						.comCcf("004667")
						.comCoo("015601")
						.cria();
		
		nfe = (NotaFiscalEletronica) new DocumentoEntradaBuilder()
				.nfe()
				.naData(LocalDate.now())
				.comNumero("000175")
				.comFrete(new BigDecimal(22.33))
				.comSubTotal(new BigDecimal(1000))
				.daSerie("1")
				.cria();
		
	}

	@Test
	public void deveRetornarOTipo() {
		
		assertEquals(TipoDocumentoEntrada.CUPOM_FISCAL, cupomFiscal.tipo());
		assertEquals(TipoDocumentoEntrada.NFE, nfe.tipo());
	}
	
	@Test
	public void deveCalcularOTotalDoDocumento() {
		
		assertEquals(new BigDecimal(232.80).doubleValue(), cupomFiscal.total().doubleValue(), 0.01);
		assertEquals(new BigDecimal(1022.33).doubleValue(), nfe.total().doubleValue(), 0.01);
	}

}
