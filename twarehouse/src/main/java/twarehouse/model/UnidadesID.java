/**
 * 
 */
package twarehouse.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author Sidronio
 *
 */
@Embeddable
public class UnidadesID implements Serializable {

	private static final long serialVersionUID = 125681575380171952L;
	
	private Long produtoCodigo;
	
	private Long unidadeEntrada;
	private Long unidadeSaida;
	
	public UnidadesID() {	}
	
	public UnidadesID(Long produtoCodigo, Long unidadeEntrada, Long unidadeSaida) {
		this.produtoCodigo = produtoCodigo;
		this.unidadeEntrada = unidadeEntrada;
		this.unidadeSaida = unidadeSaida;
	}

	public Long getProdutoCodigo() {
		return produtoCodigo;
	}
	public void setProdutoCodigo(Long produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}
	
	public Long getUnidadeEntrada() {
		return unidadeEntrada;
	}
	public void setUnidadeEntrada(Long unidadeEntrada) {
		this.unidadeEntrada = unidadeEntrada;
	}
	
	public Long getUnidadeSaida() {
		return unidadeSaida;
	}
	public void setUnidadeSaida(Long unidadeSaida) {
		this.unidadeSaida = unidadeSaida;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produtoCodigo == null) ? 0 : produtoCodigo.hashCode());
		result = prime * result + ((unidadeEntrada == null) ? 0 : unidadeEntrada.hashCode());
		result = prime * result + ((unidadeSaida == null) ? 0 : unidadeSaida.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnidadesID other = (UnidadesID) obj;
		if (produtoCodigo == null) {
			if (other.produtoCodigo != null)
				return false;
		} else if (!produtoCodigo.equals(other.produtoCodigo))
			return false;
		if (unidadeEntrada == null) {
			if (other.unidadeEntrada != null)
				return false;
		} else if (!unidadeEntrada.equals(other.unidadeEntrada))
			return false;
		if (unidadeSaida == null) {
			if (other.unidadeSaida != null)
				return false;
		} else if (!unidadeSaida.equals(other.unidadeSaida))
			return false;
		return true;
	}
	
}
