package twarehouse.model;

public enum TipoDocumentoEntrada {

	NOTA_FISCAL("Nota Fiscal"),
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
