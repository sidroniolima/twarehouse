/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.SetorDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Setor;

/**
 * Camada Service para a Entidade Setor.
 * 
 * @author Sidronio
 * 26/10/2015
 */
@Stateless
public class SetorService implements Serializable{

	private static final long serialVersionUID = 6280493136320764147L;
	
	@Inject
	private SetorDAO setorDAO;
	
	/**
	 * Chama o método DAO que persiste a entidade setor.
	 * 
	 * @param setor
	 * @throws RegraDeNegocioException 
	 */
	public void salva(Setor setor) throws RegraDeNegocioException {
		
		this.validaInsercao(setor);
		this.setorDAO.salvar(setor);
	}
	
	/**
	 * Chama o método de exclusão no DAO.
	 * 
	 * @param setor
	 * @throws RegraDeNegocioException
	 */
	public void exclui(Setor setor) throws RegraDeNegocioException {
		
		this.validaExclusao(setor);
		
		this.setorDAO.excluir(setor.getCodigo());
	}
	
	/**
	 * Chama o método buscar pelo código do DAO.
	 * 
	 * @param codigo
	 * @return
	 */
	public Setor buscaPeloCodigo(Long codigo) {
		return this.setorDAO.buscarPeloCodigo(codigo);
	}
	
	/**
	 * Valida a inserção lançando uma exceção correspondente  
	 * à uma regra de negócio violada. 
	 * 
	 * @param setor
	 * @throws RegraDeNegocioException
	 */
	public void validaInsercao(Setor setor) throws RegraDeNegocioException {
		
	}
	
	/**
	 * Valida a inserção lançando uma exceção correspondente  
	 * à uma regra de negócio violada. 
	 * 
	 * @param setor
	 * @throws RegraDeNegocioException
	 */
	public void validaExclusao(Setor setor) throws RegraDeNegocioException {
		
		if (temFuncionarios(setor)) {
			throw new RegraDeNegocioException(
					"Existem funcionários neste setor.");
		}
	}
	
	/**
	 * Informa se a instância do setor possui 
	 * produtos atribuídos a ele.
	 * 
	 * @param setor
	 * @return
	 */
	public boolean temFuncionarios(Setor setor) {
		return this.setorDAO.listarFuncionariosDoSetor(setor).size() > 0;
	}

	/**
	 * Retorna os registros da entidade setor.
	 * @return
	 */
	public List<Setor> listaTodos() {
		return setorDAO.filtrar(new Setor(), null, null, null, null);
	}
}
