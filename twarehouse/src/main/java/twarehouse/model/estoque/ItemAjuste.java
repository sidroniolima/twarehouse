/**
 * 
 */
package twarehouse.model.estoque;

import java.math.BigDecimal;
import java.math.RoundingMode;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.model.TipoDocumentoEntrada;
import twarehouse.model.Unidade;

/**
 * Itens do ajuste de estoque.
 * 
 * @author Sidronio
 * 18/11/2015
 */
public class ItemAjuste {

	private Ajuste ajuste;
	
	private Produto produto;
	private Unidade unidade;
	
	private BigDecimal qtd;
	
	/**
	 * Construtor padrão 
	 */
	public ItemAjuste() {	}

	/**
	 * Construtor com todos as variáveis de membro.
	 * 
	 * @param ajuste
	 * @param produto
	 * @param unidade
	 * @param qtd
	 */
	public ItemAjuste(Ajuste ajuste, Produto produto, Unidade unidade, BigDecimal qtd) {
		this.ajuste = ajuste;
		this.produto = produto;
		this.unidade = unidade;
		this.qtd = qtd;
	}

	/**
	 * Se não for utilizado a unidade de entrada, padrão 
	 * para as movimentações é necessário a conversão. 
	 * 
	 * @return boolean
	 * 
	 */
	public boolean ehNecessarioConverterQuantidade(){
		
		return !this.unidade.equals(produto.getUnidades().getEntrada());
	}
	
	/**
	 * Converte sempre para a unidade entrada, padrão 
	 * da movimentação.
	 * 
	 */
	public void ajustaQuantidade() {
		
		if (ehNecessarioConverterQuantidade()) {
		
			this.qtd = produto.getUnidades().converteDeSaidaParaEntrada(qtd);
		}
	}

	/**
	 * Valida um Item do Ajuste lançando uma exceção se não 
	 * houver um produto ou uma unidade e a quantidade 
	 * não for maior que zero.
	 * 
	 * @throws RegraDeNegocioException 
	 */
	public void valida() throws RegraDeNegocioException {
		
		if (null == produto) {
			throw new RegraDeNegocioException("A movimentacao deve ser de um produto.");
		}
		
		if (null == this.unidade) {
			throw new RegraDeNegocioException("Deve ser definida uma unidade.");
		}
		
		if (qtd.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RegraDeNegocioException("A quantidade deve ser maior que 0.");
		}		
	}
	
	public Ajuste getAjuste() {
		return ajuste;
	}
	public void setAjuste(Ajuste ajuste) {
		this.ajuste = ajuste;
	}

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getQtd() {
		return qtd;
	}
	public void setQtd(BigDecimal qtd) {
		this.qtd = qtd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ItemAjuste other = (ItemAjuste) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}
	
}
