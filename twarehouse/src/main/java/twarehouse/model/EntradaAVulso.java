/**
 * 
 */
package twarehouse.model;

import java.math.BigDecimal;

/**
 * Um documento de entrada a vulso, sem documentação.
 * 
 * @author Sidronio
 * 26/11/2015
 */
public class EntradaAVulso extends DocumentoEntrada {

	private static final long serialVersionUID = -2216964131119275652L;

	@Override
	public BigDecimal total() {
		return this.getSubtotal();
	}

	@Override
	public TipoDocumentoEntrada tipo() {
		return TipoDocumentoEntrada.AVULSO;
	}

	@Override
	public String identificao() {
		return "Compra a vulso";
	}

}
