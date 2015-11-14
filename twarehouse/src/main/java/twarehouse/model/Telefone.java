/**
 * 
 */
package twarehouse.model;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import twarehouse.excpetion.RegraDeNegocioException;

/**
 * Entidade telefone utilizada para Funcionário entre outras.
 * 
 * @author Sidronio
 *
 */
@Entity
@Table(name="telefone")
@Vetoed
public class Telefone implements Serializable {

	private static final long serialVersionUID = -3121498389349566578L;

	@EmbeddedId
	private TelefonePK pk;
	
	@Enumerated(EnumType.STRING)
	private Operadora operadora;
	
	@Column(columnDefinition = "boolean default false")
	private boolean movel;
	
	/**
	 * Construtor padrão. 
	 */
	public Telefone() {
		pk = new TelefonePK();
	}

	/**
	 * Valida um telefone. Para ser válido deve possuir
	 * o DDD, número. Ainda, se móvel, a operadora. 
	 * @throws RegraDeNegocioException 
	 */
	public void valida() throws RegraDeNegocioException {
		
		if (null == this.getDdd() || this.getDdd().isEmpty()) {
			throw new RegraDeNegocioException("O telefone deve ter o DDD.");
		}
		
		if (null == this.getNumero() || this.getNumero().isEmpty()) {
			throw new RegraDeNegocioException("Não há número de telefone.");
		}
		
		if (this.isMovel()) {
			
			if (null == this.getOperadora()) {
				throw new RegraDeNegocioException("O telefone móvel deve ser de alguma operadora.");
			}
			
		}
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(String.format("(%s) %s, (R: %s)", this.getDdd(), this.getNumero(), this.getRamal()));

		if (this.isMovel()) {
			builder.append(" - " + this.getOperadora().getDescricao());
		}
		
		return builder.toString();
	}
	
	public TelefonePK getPk() {
		return pk;
	}
	public void setPk(TelefonePK id) {
		this.pk = id;
	}

	@Transient
	public String getDdd() {
		return pk.getDdd();
	}
	@Transient
	public void setDdd(String ddd) {
		pk.setDdd(ddd);
	}

	@Transient
	public String getNumero() {
		return pk.getNumero();
	}
	@Transient
	public void setNumero(String numero) {
		this.pk.setNumero(numero);
	}

	@Transient
	public String getRamal() {
		return pk.getRamal();
	}
	@Transient
	public void setRamal(String ramal) {
		this.pk.setRamal(ramal);
	}
	
	public Operadora getOperadora() {
		return operadora;
	}
	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}

	public boolean isMovel() {
		return movel;
	}
	public void setMovel(boolean movel) {
		this.movel = movel;
	}

}
