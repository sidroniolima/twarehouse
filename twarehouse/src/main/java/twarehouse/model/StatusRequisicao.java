package twarehouse.model;

public enum StatusRequisicao {

	ABERTA("Aberta"),
	REJEITADA("Rejeitada"),
	ATENDIDA("Atendida"),
	FINALIZADA("Finalizada");
	
	private String descricao;

	private StatusRequisicao(String descricao) {
		this.descricao = descricao;
	}

	public StatusRequisicao[] getStatusRequisicao() {
		return StatusRequisicao.values();
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
	
}
