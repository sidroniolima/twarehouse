/**
 * 
 */
package twarehouse.model;

/**
 * @author Sidronio
 *
 */
public class Cpf implements Documento {
	
	private String valor;
	
	public Cpf() {
	}
	
	public Cpf(String valor) {
		this.valor = valor;
	}

	@Override
	public String getValor() {
		return this.valor;
	}
	
	@Override
	public boolean ehValido() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.getValor();
	}

}
