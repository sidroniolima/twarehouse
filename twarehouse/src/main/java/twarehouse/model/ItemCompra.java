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

import twarehouse.excpetion.RegraDeNegocioException;

/**
 * @author Sidronio
 *
 */
@Entity
@Table(name="item_compra")
@Vetoed
public class ItemCompra implements Serializable {
	
	private static final long serialVersionUID = 689564928366103260L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_compra")
	private Compra compra;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_produto")
	private Produto produto;
	
	private BigDecimal qtd;
	
	@Column(name="valor_unitario")
	private BigDecimal valorUnitario;
	
	public ItemCompra() {
		valorUnitario = BigDecimal.ZERO;
		qtd = BigDecimal.ZERO;
	}
	
	public ItemCompra(Compra compra, 
			Produto produto,
			BigDecimal qtd, 
			BigDecimal valorUnitario) {
		
		this();
		
		this.compra = compra;
		this.produto = produto;
		this.qtd = qtd;
		this.valorUnitario = valorUnitario;
	}
	
	/**
	 * Valida um item:
	 * Deve ter um produto;
	 * Sua quantiade deve ser maior que zero.
	 * 
	 * @throws RegraDeNegocioException 
	 * 
	 */
	public void valida() throws RegraDeNegocioException {
		
		if (qtd.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RegraDeNegocioException("A quantidade do item deve ser maior que 0.");
		}
		
		if (null == produto) {
			throw new RegraDeNegocioException("O item deve estar associado a um produto.");
		}
		
	}
	
	/* Imprime o produto e quantiade do item.
	 */
	@Override
	public String toString() {
		return String.format("produto: %s, quantidade: %s", produto.getDescricao(), qtd);
	}
	
	/**
	 * Calcula o valor total mutiplicando a qtd 
	 * pelo unitário.
	 * 
	 * @return
	 */
	public BigDecimal valorTotal() {
		return this.getValorUnitario().multiply(qtd);
	}
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Compra getEntrada() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
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
		ItemCompra other = (ItemCompra) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
