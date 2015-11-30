/**
 * 
 */
package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.enterprise.inject.Vetoed;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Sidronio
 *
 */
@Entity
@DiscriminatorValue("CUPOM_FISCAL")
@Vetoed
public class CupomFiscal extends DocumentoEntrada implements Serializable {

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
		return this.tipo().getDescricao() + ": " + this.getCcf() + " - " + this.getCoo();
	}
	
	@Override
	public BigDecimal total() {
		return this.getSubtotal().subtract(this.getDesconto())
				.setScale(2, RoundingMode.HALF_UP);
	}
	
}
