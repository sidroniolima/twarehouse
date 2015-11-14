/**
 * 
 */
package twarehouse.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

/**
 * @author Sidronio
 *
 */
@DiscriminatorValue("NFE")
public class NotaFiscalEletronica extends DocumentoEntrada {

	private static final long serialVersionUID = 7461625214655940254L;
	
	private String numero;
	private String serie;
	
	@Column(name="valor_frete")
	private BigDecimal valorFrete;
	
	public NotaFiscalEletronica() {
		this.valorFrete = BigDecimal.ZERO;
	}
	
	@Override
	public TipoDocumentoEntrada tipo() {
		return TipoDocumentoEntrada.NFE;
	}

	@Override
	public BigDecimal total() {
		return 
			this.getSubTotal().add(valorFrete).subtract(this.getDesconto())
				.setScale(2, RoundingMode.HALF_UP);
	}
	
	@Override
	public String identificao() {
		return this.getNumero() + " - " + this.getSerie();
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}
	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

}
