/**
 * 
 */
package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Sidronio
 *
 */

@Entity
@Table(name="documento_entrada")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_documento", discriminatorType=DiscriminatorType.STRING)
@Vetoed
public abstract class DocumentoEntrada implements Serializable {

	private static final long serialVersionUID = -3930744103038954411L;

	@Id
	private Long codigo;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entrada_codigo")
	private Entrada entrada;
	
	private BigDecimal desconto;
	private BigDecimal subTotal;
	
	@Column(columnDefinition="DATE")
	private LocalDate data;
	
	public DocumentoEntrada() {	
		this.desconto = BigDecimal.ZERO;
	}
	
	@Transient
	public abstract BigDecimal total();
	
	@Transient
	public abstract TipoDocumentoEntrada tipo();
	
	@Transient
	public abstract String identificao();

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Entrada getEntrada() {
		return entrada;
	}
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoEntrada other = (DocumentoEntrada) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
