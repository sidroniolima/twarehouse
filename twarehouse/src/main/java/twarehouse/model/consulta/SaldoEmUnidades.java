package twarehouse.model.consulta;

import java.math.BigDecimal;

import twarehouse.model.Produto;
import twarehouse.molde.estoque.Almoxarifado;

/**
 * Fornece o saldo de um produto pelas unidades de 
 * entrada e saída.
 * 
 * @author Sidronio
 * 13/11/2105
 */
public class SaldoEmUnidades {

	private Almoxarifado almoxarifado;
	private Produto produto;

	private BigDecimal qtdEntrada;
	private BigDecimal qtdSaida;
	
	/**
	 * Construtor padrão.
	 */
	public SaldoEmUnidades() {	}
	
	public BigDecimal getSaldo() {
		return null;
	}
	
	public BigDecimal transformaEntradaEmSaida() {
		return null;
	}
	
	public BigDecimal transformaSaidaEmEntrada() {
		return null;
	}
		
	public Almoxarifado getAlmoxarifado() {
		return almoxarifado;
	}
	public void setAlmoxarifado(Almoxarifado almoxarifado) {
		this.almoxarifado = almoxarifado;
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public BigDecimal getQtdEntrada() {
		return qtdEntrada;
	}
	public void setQtdEntrada(BigDecimal qtdEntrada) {
		this.qtdEntrada = qtdEntrada;
	}
	
	public BigDecimal getQtdSaida() {
		return qtdSaida;
	}
	public void setQtdSaida(BigDecimal qtdSaida) {
		this.qtdSaida = qtdSaida;
	}
	
}
