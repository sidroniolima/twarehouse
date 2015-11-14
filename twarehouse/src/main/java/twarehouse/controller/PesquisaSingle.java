/**
 * 
 */
package twarehouse.controller;

/**
 * Classe abstrata para os Beans que envolvam 
 * uma entidade Master.
 * 
 * @author Sidronio
 *
 */
/**
 * @author Sidronio
 *
 */
public abstract class PesquisaSingle {

	/**
	 * Inicia os atributos do Bean.
	 */
	public abstract void init();
	
	
	/**
	 * Envia uma requisição de exclusão à camada
	 * de Serviço da Entidade.
	 */
	public abstract void excluir();
	
	/**
	 * Lista os registros com paginação.
	 */
	public abstract void listarComPaginacao();
	
	/**
	 * Mensagem de exclusão.
	 * 
	 * @param registro
	 * @return
	 */
	public abstract String getMensagemDeExclusaoOk(String registro);
	
	/**
	 * Mensagem de falha na exclusão devido a um erro 
	 * de negócio na camada Service.
	 * 
	 * @param registro
	 * @param msgError
	 * @return
	 */
	public abstract String getMensagemDeErroDeExclusao(String registro, String msgError);
}
