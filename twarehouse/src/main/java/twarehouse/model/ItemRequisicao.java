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
@Table(name="item_requisicao")
@Vetoed
public class ItemRequisicao implements Serializable{
	
	private static final long serialVersionUID = 2287114320276896280L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_requisicao")
	private Requisicao requisicao;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_produto")
	private Produto produto;
	
	@Column(name="qtd_entregue")
	private BigDecimal qtdEntregue;
	
	@Column(name="valor_unitario")
	private BigDecimal valorUnitario;
	
	@Column(name="qtd_devolvida")
	private BigDecimal qtdDevolvida;
	
	public ItemRequisicao() {
		this.qtdEntregue = BigDecimal.ZERO;
		this.valorUnitario = BigDecimal.ZERO;
		this.qtdDevolvida = BigDecimal.ZERO;
	}
	
	public ItemRequisicao(
			Requisicao requisicao, 
			Produto produto,
			BigDecimal qtdEntregue) {
		
		this();
		
		this.requisicao = requisicao;
		this.produto = produto;
		this.valorUnitario = produto.getValorCusto();
		this.qtdEntregue = qtdEntregue;
	}
	
	@Transient
	public BigDecimal valorEntregue() {
		return valorUnitario.multiply(qtdEntregue);
	}
	
	@Transient
	public BigDecimal valorDevolvido() {
		return valorUnitario.multiply(this.getQtdDevolvida());
	}
	
	@Transient
	public BigDecimal qtdUtilizada() {
		return this.getQtdEntregue().subtract(qtdDevolvida);
	}
	
	@Transient
	public BigDecimal valorUtilizado() {
		return valorUnitario.multiply(this.qtdUtilizada());
	}

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Requisicao getRequisicao() {
		return requisicao;
	}
	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public BigDecimal getQtdEntregue() {
		return qtdEntregue;
	}
	public void setQtdEntregue(BigDecimal qtdEntregue) {
		this.qtdEntregue = qtdEntregue;
	}
	
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public BigDecimal getQtdDevolvida() {
		return qtdDevolvida;
	}
	public void setQtdDevolvida(BigDecimal qtdDevolvida) {
		this.qtdDevolvida = qtdDevolvida;
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
		ItemRequisicao other = (ItemRequisicao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
