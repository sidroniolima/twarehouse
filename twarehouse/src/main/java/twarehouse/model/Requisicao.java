package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import twarehouse.model.exception.RequisicaoException;

@Entity
@Table(name="requisicao")
@Vetoed
public class Requisicao implements Serializable {
	
	private static final long serialVersionUID = 5747960052855805764L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name="data_requisicao")
	private LocalDate dataRequisicao;
	
	@Column(name="data_entrega")
	private LocalDate dataEntrega;
	
	@OneToMany(fetch=FetchType.LAZY,
			mappedBy="requisicao",
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<ItemRequisicao> itens;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_funcionario")
	private Funcionario funcionario;
	
	@Enumerated(EnumType.STRING)
	private StatusRequisicao status;
	
	private String observacao;
	
	public Requisicao(){
		
		this.itens = new ArrayList<ItemRequisicao>();
		
		this.setDataRequisicao(LocalDate.now());
		this.setStatus(StatusRequisicao.ABERTA);
	}
	
	public void devolveQtdDoProduto(Produto produto, BigDecimal qtd) throws RequisicaoException{
		
		ItemRequisicao item = new ItemRequisicao();
		
		try {
			
			item = this.localizaItemPeloProduto(produto);
		
		} catch (NoSuchElementException ex) {
			throw new RequisicaoException("Não foi encontrado este produto nesta requisição.");
		}
		
		if (qtd.compareTo(item.getQtdEntregue())> 0) {
			throw new RequisicaoException("A quantidade da devolução deve ser menor ou igual ao entregue.");
		}
		
		item.setQtdDevolvida(qtd);
	}
	
	private ItemRequisicao localizaItemPeloProduto(Produto produto) throws NoSuchElementException {
		
		return itens
				.stream()
				.filter(itemRequisicao -> itemRequisicao.getProduto().equals(produto))
				.findFirst()
				.get();
		
	}

	public void finaliza() {
		this.setStatus(StatusRequisicao.FINALIZADA);
		
	}	
	
	public void atende() {
		setStatus(StatusRequisicao.ATENDIDA);
		
	}	
	
	@Transient
	public BigDecimal qtdItensEntregues() {
		return itens
				.stream()
				.map(ItemRequisicao::getQtdEntregue)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Transient
	public BigDecimal valorEntregue() {
		return itens
				.stream()
				.map(ItemRequisicao::valorEntregue)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	@Transient
	public BigDecimal qtdItensDevolvidos() {
		return itens
				.stream()
				.map(ItemRequisicao::getQtdDevolvida)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	@Transient
	public BigDecimal valorDevolvido() {
		return itens
				.stream()
				.map(ItemRequisicao::valorDevolvido)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	@Transient
	public BigDecimal qtdItensUtilizados() {
		return itens
				.stream()
				.map(ItemRequisicao::qtdUtilizada)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}	
	
	@Transient
	public BigDecimal valorUtilizado() {
		return itens
				.stream()
				.map(ItemRequisicao::valorUtilizado)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void adicionaItem(ItemRequisicao itemRequisicao) {
		itemRequisicao.setRequisicao(this);
		this.itens.add(itemRequisicao);
	}
	
	public void adicionaProduto(Produto produto, BigDecimal qtd) {
		this.itens.add(new ItemRequisicao(this, produto, qtd));
	}
	
	@Transient
	public boolean isNova() {
		return this.codigo == null;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public LocalDate getDataRequisicao() {
		return dataRequisicao;
	}
	public void setDataRequisicao(LocalDate dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public List<ItemRequisicao> getItens() {
		return itens;
	}
	public void setItens(List<ItemRequisicao> itens) {
		this.itens = itens;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public StatusRequisicao getStatus() {
		return status;
	}
	public void setStatus(StatusRequisicao status) {
		this.status = status;
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
		Requisicao other = (Requisicao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("Codigo: %s \t "
						+ "Funcionario: %s \t "
						+ "Status: %s \t "
						+ "Qtd entregue: %s \t "
						+ "Valor entregue: %s \t "
						+ "Qtd devolvida: %s \t "
						+ "Valor devolvido: %s \t "
						+ "Qtd Utilizada: %s \t "
						+ "Valor utilizado: %s",
						codigo, 
						funcionario.getNome(), 
						status.toString(), 
						qtdItensEntregues(), 
						valorEntregue(),
						qtdItensDevolvidos(), 
						valorDevolvido(),
						qtdItensUtilizados(), 
						valorUtilizado());
	}

}
