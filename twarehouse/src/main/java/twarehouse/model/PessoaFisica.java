package twarehouse.model;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import twarehouse.converter.JpaCpfConverter;

/**
 * Estensão da abstração Pessoa que representa as físicas.
 * 
 * @author Sidronio
 *
 */
@Entity
@DiscriminatorValue("FISICA")
@Vetoed
public class PessoaFisica extends Pessoa implements Serializable {

	private static final long serialVersionUID = 671407213456872590L;
	
	@Convert(converter=JpaCpfConverter.class)
	private Documento cpf;
	
	private String nome;

	public Documento getCpf() {
		return cpf;
	}
	public void setCpf(Documento cpf) {
		this.cpf = cpf;
	}

	@Override
	public TipoPessoa getTipo() {
		return TipoPessoa.FISICA;
	}

	@Override
	public Documento getDocumentoPrincipal() {
		return this.cpf;
	}

	@Override
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}