/**
 * 
 */
package twarehouse.service.impl;

import java.io.Serializable;
import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.CompraDAO;
import twarehouse.dao.GenericDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Compra;
import twarehouse.service.CompraService;
import twarehouse.service.SimpleServiceLayerImpl;

/**
 * Implementação da camada Service da entidade 
 * Compra.
 * 
 * @author Sidronio
 * 26/11/2015
 */
@Stateless
public class CompraServiceImpl extends SimpleServiceLayerImpl<Compra, Long> implements CompraService, Serializable {

	private static final long serialVersionUID = 8830512301515474398L;

	@Inject
	private CompraDAO compraDAO;

	@Override
	public void validaInsercao(Compra compra) throws RegraDeNegocioException {
		compra.valida();
	}

	@Override
	public void validaExclusao(Compra compra) throws RegraDeNegocioException {
		
	}

	@Override
	public GenericDAO getDAO() {
		return this.compraDAO;
	}

	@Override
	public Compra buscaPeloCodigoCompleta(Long codigo) {
		
		return this.compraDAO.buscarPeloCodigoComRelacionamento(
				codigo,
				Arrays.asList("docuemnto", "fornecedor", "itens"));
	}
	

}
