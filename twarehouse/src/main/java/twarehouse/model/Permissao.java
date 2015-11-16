package twarehouse.model;

public enum Permissao {

	USER_ADMIN("Administrador"),
	USER_COMMON("Operacional"),
	USER_FINANCIAL("Financeiro");
	
	private String descricao;
	
	Permissao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Permissao[] getValues() {
		return Permissao.values();
	}
	
}
