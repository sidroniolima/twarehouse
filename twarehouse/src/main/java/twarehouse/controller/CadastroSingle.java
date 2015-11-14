/**
 * 
 */
package twarehouse.controller;

/**
 * Classe abstract para os Bean de Cadastro que envolvem apenas 
 * uma Entidade Master.
 * 
 * @author Sidronio
 *
 * 22/10/2015
 *
 */
/**
 * @author Sidronio
 *
 */
public abstract class CadastroSingle {

	/**
	 * Inicia o Bean através da anotação PostConstruct 
	 * criando uma nova instância da Entidade pelo parâmetro passado 
	 * ou um nova.
	 * 
	 */
	abstract void init();
	
	/**
	 * Inicia uma nova Instância do objeto.
	 */
	abstract void novoRegistro();	
	
	
	/**
	 * Encaminha a requisição de salvar uma instância à camada 
	 * Service.
	 * 
	 */
	public abstract void salvar();
	
	/**
	 * Determina se é uma edição do registro.
	 * 
	 * @return
	 */
	abstract boolean isEdicao();
	
	/**
	 * Utilizada na finalização da inclusão de um registro.
	 * 
	 * @return
	 */
	abstract String getMensagemDeInclusao(String registro);
	
	/**
	 * Utilizada na alteração de um registro.
	 * 
	 * @return
	 */
	abstract String getMensagemDeAlteracao(String registro);
}
