/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.GenericDAO;
import twarehouse.dao.UnidadeDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Unidade;
import twarehouse.service.SimpleServiceLayerImpl;
import twarehouse.service.UnidadeService;

/**
 * Implementação da camada Service para a entidade Unidade.
 * 
 * @author Sidronio
 * 11/11/2015
 */
@Stateless
public class UnidadeServiceImpl extends SimpleServiceLayerImpl<Unidade, Long> implements UnidadeService, Serializable {

	@Inject
	private UnidadeDAO unidadeDAO;
	
	@Override
	public void validaInsercao(Unidade unidade) throws RegraDeNegocioException {
		
		if (null == unidade.getDescricao() || unidade.getDescricao().isEmpty()) {
			throw new RegraDeNegocioException("A unidade deve ter uma descrição.");
		}
		
		if (null == unidade.getSigla() || unidade.getSigla().isEmpty()) {
			throw new RegraDeNegocioException("A unidade deve ter uma sigla correspondente.");
		}
	}

	@Override
	public void validaExclusao(Unidade unidade) throws RegraDeNegocioException {
		if (this.temProdutoComAUnidade(unidade)) {
			throw new RegraDeNegocioException("A unidade está sendo utilizada.");
		}
	}

	@Override
	public boolean temProdutoComAUnidade(Unidade unidade) {
		return this.unidadeDAO.buscarQtdDeProdutosComAUnidade(unidade) > 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GenericDAO getDAO() {
		return unidadeDAO;
	}


}
