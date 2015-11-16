/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.EstoqueDAO;
import twarehouse.service.DashboardService;

/**
 * Implementação da interface da camada Service  do
 * Dashboard.
 * 
 * @author Sidronio
 * 16/11/2015
 */ 
@Stateless
public class DashboardServiceImpl implements Serializable, DashboardService{

	private static final long serialVersionUID = 7983370803742594825L;

	@Inject
	private EstoqueDAO estoqueDAO;
	
	@Override
	public BigInteger qtdDeProdutosEsgotados() {
		return estoqueDAO.qtdDeProdutosEsgotados();
	}

	@Override
	public BigInteger qtdDeProdutosEmReposicao() {
		return estoqueDAO.qtdDeProdutosEmReposicao();
	}

}
