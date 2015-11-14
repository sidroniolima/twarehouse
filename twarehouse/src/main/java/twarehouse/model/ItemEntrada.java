package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="item_entrada")
@Vetoed
public class ItemEntrada implements Serializable {
	
	private static final long serialVersionUID = 689564928366103260L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_entrada")
	private Entrada entrada;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_produto")
	private Produto produto;
	
	private BigDecimal qtd;
	
	@Column(name="valor_unitario")
	private BigDecimal valorUnitario;
	
	public ItemEntrada() {
		valorUnitario = BigDecimal.ZERO;
		qtd = BigDecimal.ZERO;
	}
	
	public ItemEntrada(Entrada entrada, 
			Produto produto,
			BigDecimal qtd, 
			BigDecimal valorUnitario) {
		
		this();
		
		this.entrada = entrada;
		this.produto = produto;
		this.qtd = qtd;
		this.valorUnitario = valorUnitario;
	}
	
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

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public BigDecimal getQtd() {
		return qtd;
	}
	public void setQtd(BigDecimal qtd) {
		this.qtd = qtd;
	}
	
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@Transient
	public BigDecimal getValorTotal() {
		return this.getValorUnitario().multiply(qtd);
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
		ItemEntrada other = (ItemEntrada) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
