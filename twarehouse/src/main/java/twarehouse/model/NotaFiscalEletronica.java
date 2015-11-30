/**
 * 
 */
package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Sidronio
 *
 */
@Entity
@DiscriminatorValue("NFE")
@Vetoed
public class NotaFiscalEletronica extends DocumentoEntrada implements Serializable{

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
			this.getSubtotal().add(valorFrete).subtract(this.getDesconto())
				.setScale(2, RoundingMode.HALF_UP);
	}
	
	@Override
	public String identificao() {
		return this.tipo().getDescricao() + ": " + this.getNumero() + " - " + this.getSerie();
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
