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
public class DocumentoID implements Serializable {

	private static final long serialVersionUID = 119213660123416282L;
	
	private Long numero;
	private TipoDocumentoEntrada tipo;
	
	public DocumentoID() {	}

	public DocumentoID(Long numero, TipoDocumentoEntrada tipo) {
		this.numero = numero;
		this.tipo = tipo;
	}

	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public TipoDocumentoEntrada getTipo() {
		return tipo;
	}
	public void setTipo(TipoDocumentoEntrada tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		DocumentoID other = (DocumentoID) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
}
