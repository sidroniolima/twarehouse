/**
 * 
 */
package twarehouse.model;

/**
 * @author Sidronio
 *
 */
public enum TipoPessoa {

	FISICA("Fisica"),
	JURIDICA("Juridica");
	
	private String descricao;
	
	private TipoPessoa() {	}

	private TipoPessoa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public TipoPessoa[] getValues() {
		return TipoPessoa.values();
	}
}
