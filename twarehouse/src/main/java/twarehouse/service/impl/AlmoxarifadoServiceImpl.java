/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import twarehouse.dao.AlmoxarifadoDAO;
import twarehouse.dao.GenericDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.service.AlmoxarifadoService;
import twarehouse.service.SimpleServiceLayerImpl;
import twarehouse.util.AlmoxarifadoPrincipal;
import twarehouse.util.IConstants;

/**
 * Implementação da camada de Serviço para acesso ao DAO pela 
 * camada Controller. Responsável pelas regras de negócio atribuídas 
 * ao modelo.
 * 
 * @author Sidronio
 *
 * 06/11/2015
 */
@Stateless
public class AlmoxarifadoServiceImpl extends SimpleServiceLayerImpl<Almoxarifado, Long> implements AlmoxarifadoService, Serializable {

	private static final long serialVersionUID = -7969344480991715031L;
	
	@Inject
	private AlmoxarifadoDAO almoxarifadoDAO;

	/* Não necessário para esta Entidade.
	 * @see twarehouse.service.SimpleServiceLayer#validaInsercao(java.lang.Object)
	 */
	@Override
	public void validaInsercao(Almoxarifado almoxarifado) throws RegraDeNegocioException {
		
	}

	/* Invalida a exclusão caso o almoxarifado tenha sido usado 
	 * em alguma movimentação. 
	 * @see twarehouse.service.SimpleServiceLayer#validaExclusao(java.lang.Object)
	 */
	@Override
	public void validaExclusao(Almoxarifado almoxarifado) throws RegraDeNegocioException {
		
		if (almoxarifado.equals(this.buscaAlmoxarifadoPrincipal())) {
			throw new RegraDeNegocioException("O almoxarifado principal não pode ser excluído.");
		}
		
		if (this.almoxarifadoTemMovimentacao(almoxarifado)) {
			throw new RegraDeNegocioException("Não é possível excluir um almoxarifado utilizado em movimentação.");
		}
		
	}

	/**
	 * Indica se o almoxarifado foi utilizado em alguma movimentação.
	 * 
	 * @param almoxarifado
	 * @return True se a qtd de movimentação do almoxarifado for maior que zero.
	 */
	private boolean almoxarifadoTemMovimentacao(Almoxarifado almoxarifado) {
		return almoxarifadoDAO.almoxarifadoTemMovimentacao(almoxarifado);
	}

	/* Retorna o objeto de acesso à camada DAO injetado.
	 * 
	 * @see twarehouse.service.SimpleServiceLayerImpl#getDAO()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public GenericDAO getDAO() {
		return this.almoxarifadoDAO;
	}
	
	@SuppressWarnings("unchecked")
	@Produces
	@AlmoxarifadoPrincipal
	@Override
	public Almoxarifado buscaAlmoxarifadoPrincipal() {
		return (Almoxarifado) getDAO().buscarPeloCodigo(IConstants.CODIGO_ALMOXARIFADO_PRINCIPAL);
	}
}