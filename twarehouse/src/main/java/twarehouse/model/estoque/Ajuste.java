package twarehouse.model.estoque;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.model.Unidade;

/**
 * Classe utilizada no ajuste de estoque de um produto. Sua utilização pode 
 * ser por instância ou uma lista que posteriormente será efetuada o movimento.
 * 
 * @author Sidronio
 *
 */
public class Ajuste {
	
	private TipoMovimento tipo;
	
	private Almoxarifado almOrigem;
	private Almoxarifado almDestino;
	
	private List<ItemAjuste> movimentacao;
	
	public Ajuste() {	
		movimentacao = new ArrayList<ItemAjuste>();
	}
	
	public Ajuste(
			TipoMovimento tipo, 
			Almoxarifado almOrigem,
			Almoxarifado almDestino) {
		
		this();
		
		this.tipo = tipo;
		this.almOrigem = almOrigem;
		this.almDestino = almDestino;
	}
	
	/**
	 * Adiciona um movimento ao ajuste dado um produto e quantidade.
	 * 
	 * @param produto
	 * @param qtd
	 * @throws RegraDeNegocioException 
	 */
	public void adicionaMovimento(Produto produto, Unidade unidade, BigDecimal qtd) throws RegraDeNegocioException {
		
		ItemAjuste itemNovo = new ItemAjuste(this, produto, unidade, qtd);
		
		itemNovo.valida();
		
		movimentacao.add(itemNovo);
	}
	
	/**
	 * Adiciona um item a movimentação do Ajuste.
	 * 
	 * @param itemAjuste Item de ajuste.
	 */
	public void adicionaMovimento(ItemAjuste itemAjuste) {
		itemAjuste.setAjuste(this);
		this.movimentacao.add(itemAjuste);
	}
	
	/**
	 * Remove uma movimentação por produto pelo índice.
	 * 
	 * @param produto
	 */
	public void removeItem(ItemAjuste item) {
		movimentacao.remove(item);
	}
	
	/**
	 * Remove um item localizando pelo produto.
	 * 
	 * @param produto Produto a ser removido.
	 * @throws RegraDeNegocioException 
	 */
	public void removeItem(Produto produto) throws RegraDeNegocioException {
		
		ItemAjuste itemLocalizado = this.localizaItemPeloProduto(produto);
		
		if (null != itemLocalizado) {
			
			this.removeItem(itemLocalizado);
		} else {
			
			throw new RegraDeNegocioException(
					String.format("Não existe o produto %s no ajuste.", produto.getDescricao()));
		}
		
	}
	
	/**
	 * Localiza um item da movimentação pelo produto.
	 * 
	 * @param produto Produto a ser pesquisado nos itens.
	 * @return Item localizado ou null.
	 */
	public ItemAjuste localizaItemPeloProduto(Produto produto) {
		
		for (ItemAjuste itemAjuste : movimentacao) {
			
			if (itemAjuste.getProduto().equals(produto)) {
				
				return itemAjuste;
			}
			
		}
		
		return null;
	}
	
	/**
	 * Tamanho do ajuste de acordo com a quantidade de itens.
	 * 
	 * @return
	 */
	public int getTamanho() {
		return movimentacao.size();
	}
	
	
	/**
	 * Valida uma instância de ajuste.
	 * É considerado válido se os atributos forem preenchidos e em 
	 * caso de transferência os dois almoxarifados estiverem preenchidos.
	 */
	public void valida() throws RegraDeNegocioException {
		
		if (null == this.getTipo()) {
			throw new RegraDeNegocioException("Defina se o ajuste é uma Entrada, Saída ou Transferência.");
		}
		
		if (null == this.getAlmOrigem() && !this.isTransferencia()) {
			throw new RegraDeNegocioException("O ajuste deve ser feito em algum almoxarifado.");
		}
		
		if (this.isTransferencia() && (null == this.getAlmOrigem() || null == this.getAlmDestino())) {
			throw new RegraDeNegocioException("Para uma transferência é preciso definir os almoxarifados origem e destino.");
		}
		
		if (null == movimentacao || this.movimentacao.size() == 0) {
			throw new RegraDeNegocioException("O ajuste deve ser para ao menos um produto.");
		}
	}

	/**
	 * Indica se é uma Transferência.
	 * 
	 * @return
	 */
	public boolean isTransferencia() {
		
		if (null == tipo) {
			return false;
		}
		
		return this.getTipo().equals(TipoMovimento.TRASFERENCIA);
	}

	/**
	 * Indica se é uma Entrada.
	 * @return
	 */
	public boolean isEntrada() {
		
		if (null == tipo) {
			return false;
		}
		
		return this.getTipo().equals(TipoMovimento.ENTRADA);
	}
	
	/**
	 * Indica se é uma Saída.
	 * 
	 * @return
	 */
	public boolean isSaida() {
		
		if (null == tipo) {
			return false;
		}
		
		return this.getTipo().equals(TipoMovimento.SAIDA);
	}
	
	public TipoMovimento getTipo() {
		return tipo;
	}
	public void setTipo(TipoMovimento tipo) {
		this.tipo = tipo;
	}
	
	public OrigemMovimento getOrigem() {
		return OrigemMovimento.AJUSTE;
	}

	public Almoxarifado getAlmOrigem() {
		return almOrigem;
	}
	public void setAlmOrigem(Almoxarifado almOrigem) {
		this.almOrigem = almOrigem;
	}

	public Almoxarifado getAlmDestino() {
		return almDestino;
	}
	public void setAlmDestino(Almoxarifado almDestino) {
		this.almDestino = almDestino;
	}

	public List<ItemAjuste> getMovimentacao() {
		return movimentacao;
	}
	public void setMovimentacao(List<ItemAjuste> movimentacao) {
		this.movimentacao = movimentacao;
	}

}
