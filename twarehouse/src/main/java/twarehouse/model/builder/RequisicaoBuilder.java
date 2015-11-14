/**
 * 
 */
package twarehouse.model.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import twarehouse.model.Funcionario;
import twarehouse.model.Produto;
import twarehouse.model.Requisicao;

/**
 * @author Sidronio
 *
 */
public class RequisicaoBuilder {

	private Requisicao instancia = new Requisicao();
	
	public RequisicaoBuilder doFuncionario(Funcionario funcionario){
		this.instancia.setFuncionario(funcionario);
		
		return this;
	}
	
	public RequisicaoBuilder naData(LocalDate data){
		this.instancia.setDataRequisicao(data);
		
		return this;
	}
	
	public RequisicaoBuilder comProduto(Produto produto, BigDecimal qtd){
		this.instancia.adicionaProduto(produto, qtd);
		
		return this;
	}
	
	public Requisicao cria() {
		return this.instancia;
	}
	
}
