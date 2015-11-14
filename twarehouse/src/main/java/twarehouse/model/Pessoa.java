/**
 * 
 */
package twarehouse.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Classe abstrata que define a Pessoa utilizada 
 * na Entidade Fornecedor.
 * 
 * @author Sidronio
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="pessoa")
@DiscriminatorColumn(name="tipo_pessoa", discriminatorType=DiscriminatorType.STRING)
public abstract class Pessoa {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Retorna o CPF para pessoa física ou 
	 * CNPJ para jurídica.
	 * 
	 * @return
	 */
	public abstract Documento getDocumentoPrincipal();
	
	/**
	 * Retorna se a Pessoa é Física ou Jurídica.
	 * 
	 * @return
	 */
	public abstract TipoPessoa getTipo();
	
	/**
	 * Retorna o nome se for pessoa física ou 
	 * o nome fantasia caso seja jurídica.
	 * 
	 * @return
	 */
	public abstract String getNome();
	
	/**
	 * Verifica se uma pessoa é do Tipo Jurídica.
	 * 
	 * @return
	 */
	public boolean isJuridica() {
		return getTipo().equals(TipoPessoa.JURIDICA);
	}
}