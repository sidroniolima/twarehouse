/**
 * 
 */
package twarehouse.model;

/**
 * @author Sidronio
 *
 */
public enum Operadora {

	VIVO("Vivo"),
	CLARO("Claro"),
	OI("Oi"),
	TIM("Tim");
	
	private String descricao;
	
	private Operadora() {	}

	private Operadora(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Operadora[] getValues() {
		return Operadora.values();
	}
}
