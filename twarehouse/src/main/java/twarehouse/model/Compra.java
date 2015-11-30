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
import javax.persistence.Transient;

import twarehouse.excpetion.RegraDeNegocioException;

@Entity
@Table(name="compra")
@Vetoed
public class Compra implements Serializable {
	
	private static final long serialVersionUID = 370710749245287160L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@OneToOne(fetch=FetchType.LAZY,
			cascade=CascadeType.ALL,
			orphanRemoval=true,
			mappedBy="compra")
	private DocumentoEntrada documento;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fornecedor_codigo")
	private Fornecedor fornecedor;
	
	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="compra",
			cascade={CascadeType.ALL},
			orphanRemoval=true)
	private List<ItemCompra> itens;
	
	private String observacao;

	public Compra() {	
		this.itens = new ArrayList<ItemCompra>();
	}
	
	/**
	 * Adiciona um item já criado caso seja válido.
	 * 
	 * @param item Item de compra.
	 */
	public void adicionaItem(ItemCompra item) {
				
		item.setCompra(this);
		itens.add(item);
	}
	
	/**
	 * Adiciona um item pelo produto, quantidade e valor unitário a compra.
	 * 
	 * @param produto Produto para adição.
	 * @param qtd Quantidade do produto.
	 * @param valorUnitario Valor unitário a época da compra.
	 */
	public void adicionaProduto(Produto produto, BigDecimal qtd, BigDecimal valorUnitario){
		
		ItemCompra item = new ItemCompra(this, produto, qtd, valorUnitario);
		this.itens.add(item);
	}
	
	/**
	 * Remove um item.
	 * 
	 * @param item A ser removido.
	 */
	public void removeItem(ItemCompra item) {
		itens.remove(item);
	}
	
	/**
	 * Localiza um produto e solicita a remoção 
	 * caso exista nos itens.
	 * 
	 * @param produto Produto a ser pesquisado.
	 * @throws RegraDeNegocioException
	 */
	public void removeItem(Produto produto) throws RegraDeNegocioException {
		this.removeItem(this.localizaItemPeloProduto(produto));
	}
	
	/**
	 * Localiza um item da compra pelo produto.
	 * 
	 * @param produto Produto a ser pesquisado nos itens.
	 * @return Item de Compra caso exista ou é lançada uma exceção 
	 * informando que o produto não existe na compra.
	 * 
	 * @throws RegraDeNegocioException
	 */
	public ItemCompra localizaItemPeloProduto(Produto produto) throws RegraDeNegocioException {
		
		for (ItemCompra itemCompra : itens) {
			
			if (itemCompra.getProduto().equals(produto)) {
				return itemCompra;
			}
			
		}
		
		throw new RegraDeNegocioException("Produto não pertence a compra.");
	}
	
	/**
	 * Calcula a quantidade de itens da compra.
	 * 
	 * @return Quantidade de itens.
	 */
	public BigDecimal qtdDeItens() {
		
		return itens
				.stream()
				.map(ItemCompra::getQtd)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Calcula o valor dos itens da compra.
	 * 
	 * @return Valor dos itens.
	 */
	public BigDecimal valorDosItens() {
		
		return itens
				.stream()
				.map(ItemCompra::valorTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	/**
	 * Valida uma compra considerando:
	 * Ao menos um item;
	 * O fornecedor;
	 * O documento de entrada;
	 * 
	 * @throws RegraDeNegocioException 
	 */
	public void valida() throws RegraDeNegocioException {
		
		if (this.qtdDeItens().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RegraDeNegocioException("A compra deve possuir ao menos um item.");
		}
		
		if (null == fornecedor) {
			throw new RegraDeNegocioException("A compra deve ser feita de um fornecedor.");
		}
		
		if (null == documento) {
			throw new RegraDeNegocioException("A compra deve possuir um documento: nota fiscal, cupom, etc...");
		}
		
	}
	
	/**
	 * Bridge para a propriedade tipo de documento.
	 *  
	 * @return
	 */
	@Transient
	public String getTipoDocumento() {
		
		if (null == this.documento) {
			return "";
		}
		
		return this.documento.tipo().getDescricao();
	}
	
	/**
	 * Adiciona um documento à compra.
	 * 
	 * @param doc
	 */
	public void adicionaDocumento(DocumentoEntrada doc) {
		doc.setCompra(this);
		this.setDocumento(doc);
	}
	
	/**
	 * Imprime uma compra com as informações do 
	 * documento e fornecedor.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		
		if (null == documento) {
			return "";
		}
		
		return documento.identificao();
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
	
	public List<ItemCompra> getItens() {
		return itens;
	}
	public void setItens(List<ItemCompra> itens) {
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
		Compra other = (Compra) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
