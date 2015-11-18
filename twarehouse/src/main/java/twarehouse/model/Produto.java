package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="produto")
@Vetoed
public class Produto implements Serializable {
	
	private static final long serialVersionUID = -1051444971108316183L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_subgrupo")
	private Subgrupo subgrupo;
	
	@Column(length=60)
	private String descricao;
	
	@Column(length=18,name="codigo_de_barras")
	private String codigoBarras;
	
	@Column(name="valor_custo")
	private BigDecimal valorCusto;
	
	@Column(name="qtd_reposicao")
	private BigDecimal qtdReposicao;
	
	@Column(name="limite_superior")
	private BigDecimal limiteSuperior;	
	
	@OneToOne(
			fetch=FetchType.LAZY,
			mappedBy="produto",
			targetEntity=Unidades.class,
			cascade = {CascadeType.ALL},
			orphanRemoval=true)
	@JoinColumn(name="unidades_codigo")
	private Unidades unidades;
	
	@Column(length=10)
	private String localizacao;
	
	private String observacao;
	
	/**
	 * Adiciona ao produto unidades de entrada e saída e
	 * a razão entre elas.
	 * 
	 * @param entrada
	 * @param saida
	 * @param razao
	 */
	public void adicionaUnidades(Unidade entrada, Unidade saida, BigDecimal razao) {
		unidades = new Unidades(this, entrada, saida, razao);
	}
	
	/**
	 * Verifica se o Produto possui unidades 
	 * de medidas de entrada.
	 * 
	 * @return
	 */
	public boolean temUnidades() {
		return this.unidades != null;
	}
	
	public Produto() {	
		unidades = new Unidades();
		unidades.setProduto(this);
	}
	
	public Produto(Long codigo) {
		this();
		
		this.codigo = codigo;
	}	
	
	public Produto(String descricao) {
		this();
		
		this.descricao = descricao;
	}
	
	public Produto(Long codigo, String descricao, BigDecimal custo) {
		this();
		
		this.codigo = codigo;
		this.descricao = descricao;
		this.valorCusto = custo;
	}

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Subgrupo getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(Subgrupo subgrupo) {
		this.subgrupo = subgrupo;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	
	public BigDecimal getValorCusto() {
		return valorCusto;
	}
	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}
	
	public BigDecimal getQtdReposicao() {
		return qtdReposicao;
	}
	public void setQtdReposicao(BigDecimal qtdReposicao) {
		this.qtdReposicao = qtdReposicao;
	}
	
	public BigDecimal getLimiteSuperior() {
		return limiteSuperior;
	}
	public void setLimiteSuperior(BigDecimal limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

	public Unidades getUnidades() {
		return unidades;
	}
	public void setUnidades(Unidades unidades) {
		this.unidades = unidades;
	}
	
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Transient
	public void atualizaCusto(BigDecimal novoCusto) {
		this.setValorCusto(novoCusto);
	}

	public String codigoFormatado() {
		if (null == this.codigo) {
			return "";
		}
		
		return String.format("%06d", this.codigo);
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	
}
