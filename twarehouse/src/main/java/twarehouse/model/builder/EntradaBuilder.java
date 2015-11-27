/**
 * 
 */
package twarehouse.model.builder;

import java.math.BigDecimal;

import twarehouse.model.DocumentoEntrada;
import twarehouse.model.Compra;
import twarehouse.model.Fornecedor;
import twarehouse.model.Produto;

/**
 * @author Sidronio
 *
 */
public class EntradaBuilder {

	private Compra instancia = new Compra();
	
	public EntradaBuilder comDocumento(DocumentoEntrada documento){
		this.instancia.setDocumento(documento);
		
		return this;
	}
	
	public EntradaBuilder doFornecedor(Fornecedor fornecedor){
		this.instancia.setFornecedor(fornecedor);
		
		return this;
	}
	
	public EntradaBuilder comProduto(Produto produto, BigDecimal qtd, BigDecimal valorUnitario){
		this.instancia.adicionaProduto(produto, qtd, valorUnitario);
		return this;
	}
	
	public Compra cria() {
		return this.instancia;
	}
	
}
