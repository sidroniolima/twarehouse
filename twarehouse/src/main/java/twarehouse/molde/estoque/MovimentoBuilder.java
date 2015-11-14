package twarehouse.molde.estoque;

import java.math.BigDecimal;

import twarehouse.model.Produto;
import twarehouse.model.Unidade;

public class MovimentoBuilder {

	private Movimento instancia = new Movimento();
	
	public MovimentoBuilder saida() {
		this.instancia.setTipoMovimento(TipoMovimento.SAIDA);
		
		return this;
	}
	
	public MovimentoBuilder entrada() {
		this.instancia.setTipoMovimento(TipoMovimento.ENTRADA);
		
		return this;
	}
	
	public MovimentoBuilder doTipo(TipoMovimento tipo) {
		this.instancia.setTipoMovimento(tipo);
		return this;
	}
	
	public MovimentoBuilder doAlmoxarifado(Almoxarifado almoxarifado) {
		this.instancia.setAlmoxarifado(almoxarifado);
		
		return this;
	}
	
	public MovimentoBuilder doProduto(Produto produto) {
		this.instancia.setProduto(produto);
		
		return this;
	}
	
	public MovimentoBuilder comUnidade(Unidade unidade) {
		this.instancia.setUnidade(unidade);
		
		return this;
	}	
	
	public MovimentoBuilder comQuantidade(BigDecimal qtd) {
		this.instancia.setQtd(qtd);
		
		return this;
	}
	
	public MovimentoBuilder daOrigem(OrigemMovimento origem) {
		this.instancia.setOrigem(origem);
		
		return this;
	}
	
	public Movimento cria() {
		return instancia;
	}
	
}
