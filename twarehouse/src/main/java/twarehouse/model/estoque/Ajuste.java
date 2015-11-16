package twarehouse.model.estoque;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;

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
	
	private Map<Produto, BigDecimal> movimentacao;
	
	public Ajuste() {	
		movimentacao = new HashMap<Produto,BigDecimal>();
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
	public void adicionaMovimento(Produto produto, BigDecimal qtd) throws RegraDeNegocioException {
		
		this.validaMovimento(produto, qtd);
		
		movimentacao.put(produto, qtd);
	}
	
	/**
	 * Valida um item de Movimentação lançando uma exceção se não 
	 * houver um produto e quantidade não for maior que zero.
	 * 
	 * @param produto
	 * @param qtd
	 * @throws RegraDeNegocioException 
	 */
	private void validaMovimento(Produto produto, BigDecimal qtd) throws RegraDeNegocioException {
		
		if (null == produto) {
			throw new RegraDeNegocioException("A movimentacao deve ser de um produto.");
		}
		
		if (qtd.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RegraDeNegocioException("A quantidade deve ser maior que 0.");
		}		
	}

	/**
	 * Remove uma movimentação por produto pelo índice.
	 * 
	 * @param produto
	 */
	public void removeMovimento(Produto produto) {
		movimentacao.remove(produto);
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

	public Map<Produto, BigDecimal> getMovimentacao() {
		return movimentacao;
	}
	public void setMovimentacao(Map<Produto, BigDecimal> movimentacao) {
		this.movimentacao = movimentacao;
	}

}
