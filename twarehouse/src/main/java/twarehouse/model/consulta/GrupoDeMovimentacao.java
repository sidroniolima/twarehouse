package twarehouse.model.consulta;

import java.util.ArrayList;
import java.util.List;

import twarehouse.model.Produto;
import twarehouse.molde.estoque.Movimento;

/**
 * Dado um conjunto de movimentações de um produto  
 * agrupadas por almoxarifado, unidade e tipo, calcula  
 * o somatório de entradas e de saídas. 
 * 
 * @author Sidronio
 * 13/11/2105
 */
public class GrupoDeMovimentacao {

	private List<Movimento> movimentacao;
	
	private Produto produto;
	
	/**
	 * Construtor padrão. 
	 */
	public GrupoDeMovimentacao() {
		movimentacao = new ArrayList<Movimento>();
	}

	/**
	 * Constrói o Grupo de Movimentação com movimentações e produto.
	 * 
	 * @param movimentacao Lista de movimentação do produto.
	 * @param produto Produto cujo saldo deve ser calculado.
	 */
	public GrupoDeMovimentacao(List<Movimento> movimentacao, Produto produto) {
		this();
		
		this.movimentacao = movimentacao;
		this.produto = produto;
	}
	
	public void agrupaMovimentacoes(){
			
		
	}
	
	
	
}
