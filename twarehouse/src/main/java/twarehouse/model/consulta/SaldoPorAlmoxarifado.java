package twarehouse.model.consulta;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SaldoPorAlmoxarifado {

	private BigInteger codigoAlmoxarifado;
	private String descricaoAlmoxarifado;
	
	private BigDecimal saldo;
	
	public SaldoPorAlmoxarifado() {	}

	public BigInteger getCodigoAlmoxarifado() {
		return codigoAlmoxarifado;
	}
	public void setCodigoAlmoxarifado(BigInteger codigoAlmoxarifado) {
		this.codigoAlmoxarifado = codigoAlmoxarifado;
	}

	public String getDescricaoAlmoxarifado() {
		return descricaoAlmoxarifado;
	}
	public void setDescricaoAlmoxarifado(String descricaoAlmoxarifado) {
		this.descricaoAlmoxarifado = descricaoAlmoxarifado;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
}
