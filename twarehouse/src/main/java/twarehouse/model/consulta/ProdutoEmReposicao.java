/**
 * 
 */
package twarehouse.model.consulta;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Classe com informações do produto em reposição, isto é,
 * que sua quantidade em estoque é menor que o da reposição.
 * 
 * @author Sidronio
 * 16/11/2015
 */
public class ProdutoEmReposicao {

	private BigInteger codigoProduto;
	private String descricaoProduto;
	private String descricaoSubgrupo;
	private String unidadeEntrada;
	private String unidadeSaida;
	private BigDecimal unidadeRazao;
	
	private BigDecimal reposicao;
	private BigDecimal saldo;
	
	/**
	 * Calcula a quantidade necessária para repor o 
	 * estoque baseado na qtd de reposição.
	 * 
	 * @return
	 */
	public BigDecimal qtdNecessariaParaRepor() {
		
		return reposicao.subtract(saldo);
	}
	public BigInteger getCodigoProduto() {
		return codigoProduto;
	}
	public void setCodigoProduto(BigInteger codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	
	public String getDescricaoProduto() {
		return descricaoProduto;
	}
	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public String getDescricaoSubgrupo() {
		return descricaoSubgrupo;
	}
	public void setDescricaoSubgrupo(String descricaoSubgrupo) {
		this.descricaoSubgrupo = descricaoSubgrupo;
	}

	public String getUnidadeEntrada() {
		return unidadeEntrada;
	}
	public void setUnidadeEntrada(String unidadeEntrada) {
		this.unidadeEntrada = unidadeEntrada;
	}

	public String getUnidadeSaida() {
		return unidadeSaida;
	}
	public void setUnidadeSaida(String unidadeSaida) {
		this.unidadeSaida = unidadeSaida;
	}

	public BigDecimal getUnidadeRazao() {
		return unidadeRazao;
	}
	public void setUnidadeRazao(BigDecimal unidadeRazao) {
		this.unidadeRazao = unidadeRazao;
	}

	public BigDecimal getReposicao() {
		return reposicao;
	}
	public void setReposicao(BigDecimal reposicao) {
		this.reposicao = reposicao;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}
