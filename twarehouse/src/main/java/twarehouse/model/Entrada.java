package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="entrada")
@Vetoed
public class Entrada implements Serializable {
	
	private static final long serialVersionUID = 370710749245287160L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@OneToOne(fetch=FetchType.LAZY,
			cascade=CascadeType.ALL,
			orphanRemoval=true,
			mappedBy="entrada")
	private DocumentoEntrada documento;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fornecedor_codigo")
	private Fornecedor fornecedor;
	
	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="entrada",
			cascade={CascadeType.ALL},
			orphanRemoval=true)
	private List<ItemEntrada> itens;
	
	private String observacao;

	public Entrada() {	
		this.itens = new ArrayList<ItemEntrada>();
	}
	
	public void adicionaItem(ItemEntrada item) {
		item.setEntrada(this);
		itens.add(item);
	}
	
	public void adicionaProduto(Produto produto, BigDecimal qtd, BigDecimal valorUnitario){
		
		ItemEntrada item = new ItemEntrada(this, produto, qtd, valorUnitario);
		this.itens.add(item);
	}
	
	public BigDecimal qtdDeItens() {
		
		return itens
				.stream()
				.map(ItemEntrada::getQtd)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal valorDosItens() {
		
		return itens
				.stream()
				.map(ItemEntrada::getValorTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public DocumentoEntrada getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoEntrada documento) {
		this.documento = documento;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public BigDecimal getDesconto() {
		return this.documento.getDesconto();
	}

	public LocalDate getData() {
		return this.documento.getData();
	}
	
	public List<ItemEntrada> getItens() {
		return itens;
	}
	public void setItens(List<ItemEntrada> itens) {
		this.itens = itens;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
		Entrada other = (Entrada) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
