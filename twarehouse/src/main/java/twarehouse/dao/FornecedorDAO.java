/**
 * 
 */
package twarehouse.dao;

import java.util.List;

import twarehouse.model.Documento;
import twarehouse.model.Entrada;
import twarehouse.model.Fornecedor;

/**
 * Interface DAO para a Entidade Fornecedor.
 * 
 * @author Sidronio
 * 04/11/2015
 * 
 */
public interface FornecedorDAO extends GenericDAO<Fornecedor, Long>{
	
	/**
	 * Pesquisa uma fornecedor pelo documento de identidade, CPF se for 
	 * pessoa física ou CNPJ se for jurídica.
	 * 
	 * @param documento
	 * @return
	 */
	public Fornecedor buscarPeloDocumentoDeIdentificacao(Documento documento);
	
	/**
	 * Lista as entradas do fornecedor.
	 * 
	 * @param fornecedor
	 * @return
	 */
	public List<Entrada> listarEntradasDoFornecedor(Fornecedor fornecedor);
	
	/**
	 * Retorna a lista de registros do filtro.
	 * 
	 * @param filtro
	 * @return
	 */
	public List<Fornecedor> filtrarFornecedorPeloNomeDocumentoOuTelefone(String nome, String documento, String numeroDeTelefone);
}
