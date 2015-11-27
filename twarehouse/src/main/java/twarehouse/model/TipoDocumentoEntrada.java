package twarehouse.model;

public enum TipoDocumentoEntrada {

	NFE("NFe"),
	CUPOM_FISCAL("Cupom fiscal"),
	AVULSO("Avulso");
	
	private String descricao;
	
	private TipoDocumentoEntrada(String descricao) {
		this.descricao = descricao;
	}
	
	public TipoDocumentoEntrada[] getTipoDocumentos(){
		return TipoDocumentoEntrada.values();
	}

	public String getDescricao() {
		return descricao;
	}
	
}
