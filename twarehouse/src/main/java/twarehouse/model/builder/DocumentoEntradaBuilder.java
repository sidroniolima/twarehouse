/**
 * 
 */
package twarehouse.model.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import twarehouse.model.CupomFiscal;
import twarehouse.model.DocumentoEntrada;
import twarehouse.model.NotaFiscalEletronica;

/**
 * @author Sidronio
 *
 */
public class DocumentoEntradaBuilder {

	private DocumentoEntrada instancia;
	
	public DocumentoEntradaBuilder cupomFiscal() {
		instancia = new CupomFiscal();
		return this;
	}
	
	public DocumentoEntradaBuilder nfe() {
		instancia = new NotaFiscalEletronica();
		return this;
	}
	
	public DocumentoEntradaBuilder comDesconto(BigDecimal desconto) {
		instancia.setDesconto(desconto);
		return this;
	}
	
	public DocumentoEntradaBuilder comSubTotal(BigDecimal subTotal) {
		instancia.setSubTotal(subTotal);
		return this;
	}
	
	public DocumentoEntradaBuilder naData(LocalDate data) {
		instancia.setData(data);
		return this;
	}
	
	public DocumentoEntradaBuilder comCcf(String ccf) {
		((CupomFiscal) instancia).setCcf(ccf);
		return this;
	}
	
	public DocumentoEntradaBuilder comCoo(String coo) {
		((CupomFiscal) instancia).setCoo(coo);
		return this;
	}
	
	public DocumentoEntradaBuilder comNumero(String numero) {
		((NotaFiscalEletronica) instancia).setNumero(numero);
		return this;
	}
	
	public DocumentoEntradaBuilder daSerie(String serie) {
		((NotaFiscalEletronica) instancia).setSerie(serie);
		return this;
	}
	
	public DocumentoEntradaBuilder comFrete(BigDecimal frete) {
		((NotaFiscalEletronica) instancia).setValorFrete(frete);
		return this;
	}
	
	public DocumentoEntrada cria() {
		return instancia;
	}
	
}
