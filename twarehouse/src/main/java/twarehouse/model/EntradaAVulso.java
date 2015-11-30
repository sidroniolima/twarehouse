/**
 * 
 */
package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Um documento de entrada a vulso, sem documentação.
 * 
 * @author Sidronio
 * 26/11/2015
 */
@Entity
@DiscriminatorValue("AVULSO")
@Vetoed
public class EntradaAVulso extends DocumentoEntrada implements Serializable {

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
