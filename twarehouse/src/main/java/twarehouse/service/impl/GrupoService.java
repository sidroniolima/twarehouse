/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.GrupoDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Grupo;

/**
 * Responsável pelas regras do padrão MVC para a 
 * entidade Grupo.
 * 
 * @author Sidronio
 * 30/09/2015
 */
@Stateless
public class GrupoService implements Serializable {

	private static final long serialVersionUID = 4176293139275447711L;
	
	@Inject
	private GrupoDAO grupoDAO;
	
	/**
	 * Chama o método salvar do DAO e lança uma exceção 
	 * caso a validação seja mal sucedida.
	 * 
	 * @param grupo
	 * @throws RegraDeNegocioException 
	 */
	public void salva(Grupo grupo) throws RegraDeNegocioException {
		
		this.validaInsercao(grupo);
		
		this.grupoDAO.salvar(grupo);
	}
	
	/**
	 * Verifica se a grupo pode ser excluída 
	 * lançando uma exceção caso haja restrição.
	 * 
	 * @param grupo
	 * @throws RegraDeNegocioException 
	 */
	public void excluir(Grupo grupo) throws RegraDeNegocioException {
		
		this.validaExclusao(grupo);
		grupoDAO.excluir(grupo.getCodigo());
	}
	
	/**
	 * Retorna todos os registros da entidade grupo.
	 * 
	 * @return
	 */
	public List<Grupo> listaTodos(){
		return grupoDAO.filtrar(new Grupo(), null, null);
	}
	
	/**
	 * Retorna todos os registros da entidade grupo 
	 * com as instâncias de família correspondentes.
	 * 
	 * @return
	 */
	public List<Grupo> listaTodosComFamilia(){
		return grupoDAO.filtrar(new Grupo(), null, Arrays.asList("familia"));
	}
	
	/**
	 * Retorna pelo código informado.
	 * 
	 * @param codigo
	 * @return
	 */
	public Grupo buscaPeloCodigo(Long codigo) {
		return this.grupoDAO.buscarPeloCodigo(codigo);
	}
	
	/**
	 * Busca o grupo pelo código e retorna o objeto 
	 * localizado com a família correspondente.
	 * 
	 * @param paramCodigo
	 * @return
	 */
	public Grupo buscaPeloCodigoComFamilia(Long codigo) {
		
		Grupo grupo = new Grupo();
		grupo.setCodigo(codigo);
		
		return grupoDAO.filtrar(
				grupo, 
				Arrays.asList("codigo"), 
				Arrays.asList("familia")).get(0);
	}
	
	/**
	 * Valida a inserção do registro.
	 * 
	 * @param grupo
	 * @throws RegraDeNegocioException
	 */
	public void validaInsercao(Grupo grupo) throws RegraDeNegocioException {
		
		if (null == grupo.getDescricao() || grupo.getDescricao().isEmpty()) {
			throw new RegraDeNegocioException("A descrição deve ser preenchida. Verifique o registro e tente novamente.");
		}
		
		if (null == grupo.getFamilia()) {
			throw new RegraDeNegocioException("Um grupo deve pertencer a uma família.");
		}
	}
	
	/**
	 * Valida a entidade e lança uma exceção caso não 
	 * atendas às regras de negócio.
	 * 
	 * @param grupo
	 * @throws RegraDeNegocioException
	 */
	public void validaExclusao(Grupo grupo) throws RegraDeNegocioException {
		
		if (this.grupoTemSubgrupos(grupo)) {
			throw new RegraDeNegocioException("Este grupo está associado a subgrupo.");
		}
	}

	/**
	 * Chama o método de busca no DAO que tráz 
	 * os Subgrupo de um Grupo.
	 * 
	 * @param grupo
	 * @return
	 */
	private List<Grupo> listaSugruposDoGrupo(Grupo grupo) {
		
		return this.grupoDAO.bucarSubgruposDoGrupo(grupo);
	}
	
	/**
	 * Verifica se o Grupo possui subgrupos associados 
	 * a ele.
	 * 
	 * @param grupo
	 * @return
	 */
	private boolean grupoTemSubgrupos(Grupo grupo) {
		return this.listaSugruposDoGrupo(grupo).size() > 0;
	}

}
