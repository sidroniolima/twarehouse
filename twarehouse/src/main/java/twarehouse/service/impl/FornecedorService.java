/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.controller.PesquisaFornecedorBean.Filtro;
import twarehouse.dao.FornecedorDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Fornecedor;
import twarehouse.util.Paginator;

/**
 * Implementação da camada de serviço para a Entidade Fornecedor.
 * Ela é responsável por regras de negócio envolvidas no padrão MVC 
 * e acesso a DAO.
 * 
 * @author Sidronio
 * 04/11/2015
 */
@Stateless
public class FornecedorService implements Serializable {

	private static final long serialVersionUID = 1780856238123095612L;

	@Inject
	private FornecedorDAO fornecedorDAO;
	
	/**
	 * Salva ou atualiza uma instância de Fornecedor.
	 * 
	 * @param fornecedor
	 */
	public void salva(Fornecedor fornecedor) {
		fornecedorDAO.salvar(fornecedor);
	}
	
	/**
	 * Exclui um fornecedor se atendias as condições 
	 * da regra de negócio.
	 * 
	 * @param fornecedor
	 */
	public void exclui(Fornecedor fornecedor) throws RegraDeNegocioException {
		
		this.validaExclusao(fornecedor);
		
		fornecedorDAO.excluir(fornecedor.getCodigo());
	}
	
	/**
	 * Lança uma exceção caso um Fornecedor possua 
	 * uma ou mais entradas cadastradas.
	 * 
	 * @param fornecedor
	 */
	public void validaExclusao(Fornecedor fornecedor) throws RegraDeNegocioException {
		
		if (this.fornecedorTemEntrada(fornecedor)) {
			throw new RegraDeNegocioException("O fornecedor possui entradas cadastradas.");
		}
		
	}
	
	/**
	 * Indica se o fornecedor tem alguma entrada cadastrada.
	 * @param fornecedor 
	 * 
	 * @return
	 */
	private boolean fornecedorTemEntrada(Fornecedor fornecedor) {
		return fornecedorDAO.listarEntradasDoFornecedor(fornecedor).size() > 0;
	}

	/**
	 * Retorna um Fornecedor pelo código.
	 * 
	 * @param codigo
	 * @return
	 */
	public Fornecedor buscaPeloCodigo(Long codigo){
		return fornecedorDAO.buscarPeloCodigo(codigo);
	}
	
	/**
	 * Retorna uma lista de fornecedores caso atenda ao filtro 
	 * especificado.
	 * 
	 * @param filtro
	 * @return
	 */
	public List<Fornecedor> filtra(Filtro filtro) {
		return fornecedorDAO.filtrarFornecedorPeloNomeDocumentoOuTelefone(
				filtro.getNome(),
				filtro.getDocumento(),
				filtro.getNumeroTelefone());
	}
	
	/**
	 * Retorna todos os fornecedores cadastrados.
	 * 
	 * @return
	 */
	public List<Fornecedor> buscaTodos() {
		return fornecedorDAO.filtrar(new Fornecedor(), null, Arrays.asList("pessoa"));
	}

	/**
	 * Retorna o registro pelo código com a Pessoa.
	 * 
	 * @param paramCodigo
	 * @return
	 */
	public Fornecedor buscaPeloCodigoComPessoaETelefones(Long codigo) {
		
		Fornecedor fornecedor = new Fornecedor(codigo);
		
		return fornecedorDAO.filtrar(fornecedor, Arrays.asList("codigo"), Arrays.asList("pessoa","telefones")).get(0);
	}
	
	public List<Fornecedor> listaComPaginacao(Paginator paginator) {
		
		return fornecedorDAO.listarComPaginacao(
				paginator.getFirstResult(), 
				paginator.getQtdPorPagina(), 
				Arrays.asList("pessoa.nome","pessoa.nomeFantasia"),
				Arrays.asList("pessoa","telefones"),
				Arrays.asList("pessoa"));
	}
	
}
