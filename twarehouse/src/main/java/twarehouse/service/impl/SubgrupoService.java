/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.SubgrupoDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Familia;
import twarehouse.model.Subgrupo;

/**
 * @author Sidronio
 *
 */
@Stateless
public class SubgrupoService implements Serializable{

	private static final long serialVersionUID = 6280493136320764147L;
	
	@Inject
	private SubgrupoDAO subgrupoDAO;
	
	/**
	 * Chama o método DAO que persiste a entidade subgrupo.
	 * 
	 * @param subgrupo
	 * @throws RegraDeNegocioException 
	 */
	public void salva(Subgrupo subgrupo) throws RegraDeNegocioException {
		
		this.validaInsercao(subgrupo);
		this.subgrupoDAO.salvar(subgrupo);
	}
	
	/**
	 * Chama o método de exclusão no DAO.
	 * 
	 * @param subgrupo
	 * @throws RegraDeNegocioException
	 */
	public void exclui(Subgrupo subgrupo) throws RegraDeNegocioException {
		
		this.validaExclusao(subgrupo);
		
		this.subgrupoDAO.excluir(subgrupo.getCodigo());
	}
	
	/**
	 * Valida a inserção lançando uma exceção correspondente  
	 * à uma regra de negócio violada. 
	 * 
	 * @param subgrupo
	 * @throws RegraDeNegocioException
	 */
	public void validaInsercao(Subgrupo subgrupo) throws RegraDeNegocioException {
		
		if (null == subgrupo.getGrupo()) {
			throw new RegraDeNegocioException(
					"O subgrupo deve estar contido em um grupo.");
		}
	}
	
	/**
	 * Valida a inserção lançando uma exceção correspondente  
	 * à uma regra de negócio violada. 
	 * 
	 * @param subgrupo
	 * @throws RegraDeNegocioException
	 */
	public void validaExclusao(Subgrupo subgrupo) throws RegraDeNegocioException {
		
		if (temProdutosCadastrados(subgrupo)) {
			throw new RegraDeNegocioException(
					"Existem produtos neste subgrupo.");
		}
	}
	
	/**
	 * Informa se a instância do subgrupo possui 
	 * produtos atribuídos a ele.
	 * 
	 * @param subgrupo
	 * @return
	 */
	public boolean temProdutosCadastrados(Subgrupo subgrupo) {
		return this.subgrupoDAO.buscarProdutosDoSubgrupo(subgrupo).size() > 0;
	}

	/**
	 * Retorna uma instância de subgrupo com o relacionamento 
	 * com Grupo pelo código.
	 * 
	 * @param paramCodigo
	 * @return
	 */
	public Subgrupo buscaPeloCodigoComGrupo(Long codigo) {
		
		return this.subgrupoDAO
				.buscarPeloCodigoComRelacionamento(
						codigo,
						Arrays.asList("grupo"));
	}
	
	/**
	 * Retorna todas os registros da entidade família.
	 * 
	 * @return
	 */
	public List<Subgrupo> listaTodas(){
		return subgrupoDAO.filtrar(new Subgrupo(), null, null, null, null);
	}
	
	/**
	 * Retorna os registros da entidade subgrupo relacionadas  
	 * com Grupo.
	 * 
	 * @return
	 */
	public List<Subgrupo> listaTodosComGrupo() {
		
		return this.subgrupoDAO
				.filtrar(new Subgrupo(), null, Arrays.asList("grupo"), null, null);
	}

}
