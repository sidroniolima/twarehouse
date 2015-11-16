/**
 * 
 */
package twarehouse.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import twarehouse.dao.GenericDAO;
import twarehouse.dao.UsuarioDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Usuario;
import twarehouse.service.SimpleServiceLayerImpl;
import twarehouse.service.UsuarioService;

/**
 * Implementação da camada Service para a entidade Usuario.
 * 
 * @author Sidronio
 * 16/11/2015
 */
@Stateless
public class UsuarioServiceImpl extends SimpleServiceLayerImpl<Usuario, Long> implements UsuarioService{

	@Inject
	private UsuarioDAO usuarioDAO;
	
	/* (non-Javadoc)
	 * @see twarehouse.service.SimpleServiceLayer#validaInsercao(java.lang.Object)
	 */
	@Override
	public void validaInsercao(Usuario usuario) throws RegraDeNegocioException {
		
		if (!usuario.temPermissoes()) {
			throw new RegraDeNegocioException("O usuário deve ter ao menos uma permissão.");
		}
		
	}

	/* (non-Javadoc)
	 * @see twarehouse.service.SimpleServiceLayer#validaExclusao(java.lang.Object)
	 */
	@Override
	public void validaExclusao(Usuario entidade) throws RegraDeNegocioException {
		
	}

	/* (non-Javadoc)
	 * @see twarehouse.service.UsuarioService#buscaUsuarioComPermissoes(java.lang.Long)
	 */
	@Override
	public Usuario buscaUsuarioComPermissoes(Long codigo) {
		
		return usuarioDAO.buscarComPermissoes(codigo);
	}

	/* (non-Javadoc)
	 * @see twarehouse.service.SimpleServiceLayerImpl#getDAO()
	 */
	@Override
	public GenericDAO getDAO() {
		return usuarioDAO;
	}

}
