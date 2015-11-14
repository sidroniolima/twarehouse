/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.FuncionarioDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Funcionario;

/**
 * Camada Service da Entidade Funcionário.
 * 
 * @author Sidronio
 *
 * 26/10/2015
 */
@Stateless
public class FuncionarioService implements Serializable {

	private static final long serialVersionUID = 7916335088403962987L;

	@Inject
	private FuncionarioDAO funcionarioDAO;
	
	/**
	 * Chama o método salvar do DAO e lança uma exceção 
	 * caso a validação seja mal sucedida.
	 * 
	 * @param funcionario
	 * @throws RegraDeNegocioException 
	 */
	public void salva(Funcionario funcionario) throws RegraDeNegocioException {
		
		this.validaInsercao(funcionario);
		
		this.funcionarioDAO.salvar(funcionario);
	}
	
	/**
	 * Verifica se a funcionário pode ser excluída 
	 * lançando uma exceção caso haja restrição.
	 * 
	 * @param funcionario
	 * @throws RegraDeNegocioException 
	 */
	public void exclui(Funcionario funcionario) throws RegraDeNegocioException {
		this.validaExclusao(funcionario);
		funcionarioDAO.excluir(funcionario.getCodigo());
	}
	
	/**
	 * Retorna todas os registros da entidade funcionário.
	 * 
	 * @return
	 */
	public List<Funcionario> listaTodos(){
		return funcionarioDAO.filtrar(new Funcionario(), null, null);
	}
	
	/**
	 * Retorna pelo código informado.
	 * 
	 * @param codigo
	 * @return
	 */
	public Funcionario buscaPeloCodigo(Long codigo) {
		return this.funcionarioDAO.buscarPeloCodigo(codigo);
	}
	
	/**
	 * Valida a inserção do registro.
	 * 
	 * @param funcionario
	 * @throws RegraDeNegocioException
	 */
	public void validaInsercao(Funcionario funcionario) throws RegraDeNegocioException {
		
		if (null == funcionario.getNome()) {
			throw new RegraDeNegocioException("Um funcionário deve ter um nome.");
		}
		
		if (null == funcionario.getSetor()) {
			throw new RegraDeNegocioException("O funcionário deve estar associado a um Setor.");
		}
	}
	
	/**
	 * Lança uma exceção caso uma regra de negócio 
	 * seja quebrada.
	 * 
	 * @param funcionario
	 * @throws RegraDeNegocioException
	 */
	public void validaExclusao(Funcionario funcionario) throws RegraDeNegocioException {
		
		if (this.funcionarioFezAlgumaRequisicao(funcionario)) {
			throw new RegraDeNegocioException("Esta funcionário possui requisições.");
		}
	}

	/**
	 * Informa se o funcionário fez alguma Requisição.
	 * 
	 * @param funcionario
	 * @return
	 */
	private boolean funcionarioFezAlgumaRequisicao(Funcionario funcionario) {
		return false;
	}

	/**
	 * Retorna um funcionário com o Setor.
	 * 
	 * @param paramCodigo
	 * @return
	 */
	public Funcionario buscaPeloCodigoComSetorETelefones(Long codigo) {
		
		Funcionario funcionario = new Funcionario(codigo);
		
		return funcionarioDAO.filtrar(
				funcionario, 
				Arrays.asList("codigo"), 
				Arrays.asList("setor","telefones")).get(0);
	}

	public Funcionario buscaPelaMatricula(String matricula) {
		
		Funcionario funcionario = new Funcionario();
		funcionario.setMatricula(matricula);
		
		return funcionarioDAO.filtrar(
				funcionario, 
				Arrays.asList("matricula"), 
				null).get(0);
	}

	/**
	 * Devolve uma lista de Funcionários com Setores de forma 
	 * paginada.
	 * 
	 * @param firstResult
	 * @param qtdPorPagina
	 */
	public List<Funcionario> listaTodosComPaginacao(int firstResult, int qtdPorPagina) {
		return this.funcionarioDAO.listarComSetorEPaginacao(firstResult, qtdPorPagina);
	}

}
