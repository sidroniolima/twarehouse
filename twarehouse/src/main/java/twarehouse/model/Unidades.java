/**
 * 
 */
package twarehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * A conversão se dá na razão de 1 unidade de Entrada 
 * para 1 de saída. Isto é, um Kg equivale a 1000 gr.
 * 
 * A lógica é a mesma para grandezas não equivalentes. Se 
 * a entrada de um produto se der por Kg mas for utilizado Cm, 
 * a razão também deve obdecer a regra acima. Assim, 1 Kg equivale a 
 * 200 centímetros, com a razão 1/200;
 * 
 * @author Sidronio
 *
 */
@Entity
@Table(name="produto_unidade")
@Vetoed
public class Unidades implements Serializable {

	private static final long serialVersionUID = -1767486642325088272L;

	@EmbeddedId
	private UnidadesID pk = new UnidadesID();	
	
	@MapsId("produtoCodigo")
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="produto_codigo")
	private Produto produto;	
	
	@MapsId("unidadeEntrada")
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="unidade_entrada")
	private Unidade entrada;
	
	@MapsId("unidadeSaida")
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="unidade_saida")
	private Unidade saida;
	
	private BigDecimal razao;
	
	public Unidades() {	
		this.razao = BigDecimal.ZERO;
	}
	
	public Unidades(Produto produto, Unidade entrada, Unidade saida, BigDecimal razao) {
		this();
		
		this.produto = produto;
		this.entrada = entrada;
		this.saida = saida;
		this.razao = razao;
	}
	
	public BigDecimal converteDeEntradaParaSaida(BigDecimal qtd) {
		return qtd.multiply(this.razao).setScale(2);
	}
	
	public BigDecimal converteDeSaidaParaEntrada(BigDecimal qtd) {
		return qtd.divide(this.razao).setScale(2);
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Unidade getEntrada() {
		return entrada;
	}
	public void setEntrada(Unidade entrada) {
		this.entrada = entrada;
	}

	public Unidade getSaida() {
		return saida;
	}
	public void setSaida(Unidade saida) {
		this.saida = saida;
	}

	public BigDecimal getRazao() {
		return razao;
	}
	public void setRazao(BigDecimal razao) {
		this.razao = razao;
	}

}
