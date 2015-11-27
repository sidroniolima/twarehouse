/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.ProdutoDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Familia;
import twarehouse.model.Produto;
import twarehouse.model.estoque.Movimento;
import twarehouse.util.Paginator;

/**
 * Class da camada Service responsável pelas regras 
 * do padrão MVC para a entidade Produto e de acesso aos métodos
 * fornecidos na camada DAO.
 * 
 * @author Sidronio
 *
 * 22/10/2015
 */
@Stateless
public class ProdutoService implements Serializable {

	private static final long serialVersionUID = 7931077631436124499L;
	
	@Inject
	private ProdutoDAO produtoDAO;
	
	/**
	 * Retorna uma instância de Produto pelo código.
	 * 
	 * @param codigo
	 * @return
	 */
	public Produto buscaPeloCodigo(Long codigo) {
		return produtoDAO.buscarPeloCodigo(codigo);
	}
	
	/**
	 * Encaminha a requisição de persistir a entidade 
	 * à camada DAO.
	 * 
	 * @param produto
	 * @throws RegraDeNegocioException 
	 */
	public void salva(Produto produto) throws RegraDeNegocioException {
		
		this.validaInclusao(produto);
		
		produtoDAO.salvar(produto);
	}
	
	/**
	 * Verifica se um Produto é válido e possui as Unidades de 
	 * medias válidas.
	 * @throws RegraDeNegocioException 
	 */
	private void validaInclusao(Produto produto) throws RegraDeNegocioException {
		
		if (!produto.temUnidades()) {
			throw new RegraDeNegocioException("O produto deve possuir as unidades e a razão entre elas.");
		}
		
	}
	
	/**
	 * Retorna todas os registros da entidade família.
	 * 
	 * @return
	 */
	public List<Produto> listaTodos(){
		return produtoDAO.filtrar(new Produto(), null, null, null, null);
	}
	
	/**
	 * Encaminha a exclusão ao método DAO depois de 
	 * validá-la.
	 * 
	 * @param produto
	 * @throws RegraDeNegocioException 
	 */
	public void exclui(Produto produto) throws RegraDeNegocioException {
		
		this.validaExclusao();
		
		produtoDAO.excluir(produto.getCodigo());
	}

	/**
	 * Valida, permitindo ou não a exclusão. O produto para ser
	 * excluído não pode ter sido utilizado, isto é, item de Entrada 
	 * ou Requisiçao.
	 */
	private void validaExclusao() throws RegraDeNegocioException {
		//TODO: validar exclusão de produto. 
	}
	
	/**
	 * Lista os registros com paginação.
	 * 
	 * @param paginator
	 * @return
	 */
	public List<Produto> listaComPaginacao(Paginator paginator) {
		return produtoDAO.listaComPaginacao(paginator.getFirstResult(), paginator.getQtdPorPagina());
	}

	/**
	 * Retorna uma instância de Produto pelo código 
	 * com o Subgrupo.
	 * 
	 * @param codigo
	 * @return
	 */
	public Produto buscaPeloCodigoComSubgrupoEUnidades(Long codigo) {

		return produtoDAO.buscarPeloCodigoComRelacionamento(
				codigo, 
				Arrays.asList("subgrupo", "unidades"));
	}
}
