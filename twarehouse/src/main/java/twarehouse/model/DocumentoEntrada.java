/**
 * 
 */
package twarehouse.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import twarehouse.converter.LocalDateDBConverter;

/**
 * @author Sidronio
 *
 */

@Entity
@Table(name="documento_entrada")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_documento", discriminatorType=DiscriminatorType.STRING)
public abstract class DocumentoEntrada {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	private BigDecimal desconto;
	private BigDecimal subtotal;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="compra_codigo")
	private Compra compra;
	
	@Column(columnDefinition="DATE", name="data")
	@Convert(converter=LocalDateDBConverter.class)
	private LocalDate data;
	
	public DocumentoEntrada() {	
		this.desconto = BigDecimal.ZERO;
		this.subtotal = BigDecimal.ZERO;
		this.data = LocalDate.now();
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
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
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
