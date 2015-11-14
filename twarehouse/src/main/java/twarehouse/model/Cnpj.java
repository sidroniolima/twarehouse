/**
 * 
 */
package twarehouse.model;

/**
 * @author Sidronio
 *
 */
public class Cnpj implements Documento {

	private String valor;
	
	public Cnpj() {	}
	
	public Cnpj(String valor) {
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
