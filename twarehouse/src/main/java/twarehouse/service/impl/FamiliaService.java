/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.FamiliaDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Familia;

/**
 * Responsável pelas regras do padrão MVC para a 
 * entidade Familia.
 * 
 * @author Sidronio
 * 28/09/2015
 */
@Stateless
public class FamiliaService implements Serializable {

	private static final long serialVersionUID = -1780663801109998845L;
	
	@Inject
	private FamiliaDAO familiaDAO;
	
	/**
	 * Chama o método salvar do DAO e lança uma exceção 
	 * caso a validação seja mal sucedida.
	 * 
	 * @param familia
	 * @throws RegraDeNegocioException 
	 */
	public void salva(Familia familia) throws RegraDeNegocioException {
		
		this.validaInsercao(familia);
		
		this.familiaDAO.salvar(familia);
	}
	
	/**
	 * Verifica se a família pode ser excluída 
	 * lançando uma exceção caso haja restrição.
	 * 
	 * @param familia
	 * @throws RegraDeNegocioException 
	 */
	public void exclui(Familia familia) throws RegraDeNegocioException {
		this.validaExclusao(familia);
		familiaDAO.excluir(familia.getCodigo());
	}
	
	/**
	 * Retorna todas os registros da entidade família.
	 * 
	 * @return
	 */
	public List<Familia> listaTodas(){
		return familiaDAO.filtrar(new Familia(), null, null, null, null);
	}
	
	/**
	 * Retorna pelo código informado.
	 * 
	 * @param codigo
	 * @return
	 */
	public Familia buscaPeloCodigo(Long codigo) {
		return this.familiaDAO.buscarPeloCodigo(codigo);
	}
	
	/**
	 * Valida a inserção do registro.
	 * 
	 * @param familia
	 * @throws RegraDeNegocioException
	 */
	public void validaInsercao(Familia familia) throws RegraDeNegocioException {
		
		if (null == familia.getDescricao() || familia.getDescricao().isEmpty()) {
			throw new RegraDeNegocioException("A descrição deve ser preenchida. Verifique o registro e tente novamente.");
		}
	}
	
	/**
	 * Lança uma exceção caso uma regra de negócio 
	 * seja quebrada.
	 * 
	 * @param familia
	 * @throws RegraDeNegocioException
	 */
	public void validaExclusao(Familia familia) throws RegraDeNegocioException {
		
		if (this.familiaPossuiGrupos(familia)) {
			throw new RegraDeNegocioException("Esta família possui grupos de produto associados.");
		}
	}

	/**
	 * Informa se uma instância de família possui 
	 * grupos cadastrados.
	 * 
	 * @param familia
	 * @return
	 */
	private boolean familiaPossuiGrupos(Familia familia) {
		return this.familiaDAO.buscaGruposDaFamilia(familia).size() > 0;
	}
	
}
