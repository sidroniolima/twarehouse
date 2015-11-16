/**
 * 
 */
package twarehouse.model.consulta;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Classe responsável por agrupar e gerar as informações 
 * disponíveis no dashboard.
 * 
 * @author Sidronio
 * 16/11/2015
 */
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 6619414669281940935L;
	
	private BigInteger qtdProdutosEmReposicao;
	private BigInteger qtdProdutosEsgotados;
	
	/**
	 * Construtor padrão.
	 */
	public Dashboard() {
		qtdProdutosEmReposicao = BigInteger.ZERO;
		qtdProdutosEsgotados = BigInteger.ZERO;
	}

	public BigInteger getQtdProdutosEmReposicao() {
		return qtdProdutosEmReposicao;
	}
	public void setQtdProdutosEmReposicao(BigInteger qtdProdutosEmReposicao) {
		this.qtdProdutosEmReposicao = qtdProdutosEmReposicao;
	}
	
	public BigInteger getQtdProdutosEsgotados() {
		return qtdProdutosEsgotados;
	}
	public void setQtdProdutosEsgotados(BigInteger qtdProdutosEsgotados) {
		this.qtdProdutosEsgotados = qtdProdutosEsgotados;
	}
	
}
