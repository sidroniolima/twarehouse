/**
 * 
 */
package twarehouse.model.consulta;

import java.time.LocalDate;

import twarehouse.model.Fornecedor;
import twarehouse.model.Produto;
import twarehouse.model.TipoDocumentoEntrada;

/**
 * Classe filtro da entidade Entrada na pesquisa 
 * com as propriedades de tipo de documento, 
 * data, fornecedor e produto. 
 * 
 * @author Sidronio
 * 
 * 30/11/2015
 */
public class FiltroEntrada {

	private TipoDocumentoEntrada tipo;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private Fornecedor fornecedor;
	private Produto produto;
	
	public TipoDocumentoEntrada getTipo() {
		return tipo;
	}
	public void setTipo(TipoDocumentoEntrada tipo) {
		this.tipo = tipo;
	}
	
	public LocalDate getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
