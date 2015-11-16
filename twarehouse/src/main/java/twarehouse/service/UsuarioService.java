/**
 * 
 */
package twarehouse.service;

import twarehouse.model.Usuario;

/**
 * Interface da camada Service para a entidade Usuario.
 * 
 * @author Sidronio
 * 16/11/2105
 */
public interface UsuarioService extends SimpleServiceLayer<Usuario, Long>{

	/**
	 * Busca as informações do usuário e as permissões 
	 * associadas a ele pelo código.
	 * 
	 * @param codigo
	 * @return
	 */
	public Usuario buscaUsuarioComPermissoes(Long codigo);
	
}
