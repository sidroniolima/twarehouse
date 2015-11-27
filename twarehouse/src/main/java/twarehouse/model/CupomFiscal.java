/**
 * 
 */
package twarehouse.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.DiscriminatorValue;

/**
 * @author Sidronio
 *
 */
@DiscriminatorValue("CUPOM_FISCAL")
public class CupomFiscal extends DocumentoEntrada {

	private static final long serialVersionUID = -7221105337713266331L;
	
	private String ccf;
	private String coo;
	
	public String getCcf() {
		return ccf;
	}
	public void setCcf(String ccf) {
		this.ccf = ccf;
	}

	public String getCoo() {
		return coo;
	}
	public void setCoo(String coo) {
		this.coo = coo;
	}

	@Override
	public TipoDocumentoEntrada tipo() {
		return TipoDocumentoEntrada.CUPOM_FISCAL;
	}

	@Override
	public String identificao() {
		return this.getCcf() + " - " + this.getCoo();
	}
	
	@Override
	public BigDecimal total() {
		return this.getSubtotal().subtract(this.getDesconto())
				.setScale(2, RoundingMode.HALF_UP);
	}
	
}
