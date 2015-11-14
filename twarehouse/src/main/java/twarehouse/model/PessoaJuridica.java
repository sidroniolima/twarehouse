package twarehouse.model;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import twarehouse.converter.JpaCnpjConverter;;

/**
 * Extensão da abstração Pessoa que representa as jurídicas.
 * 
 * @author Sidronio
 *
 */
@Entity
@DiscriminatorValue("JURIDICA")
@Vetoed
public class PessoaJuridica extends Pessoa implements Serializable {

	private static final long serialVersionUID = 671407213456872590L;
	
	@Convert(converter=JpaCnpjConverter.class)
	private Documento cnpj;

	@Column(name="razao_social")
	private String razaoSocial;
	
	@Column(name="nome_fantasia")
	private String nomeFantasia;
	
	public Documento getCnpj() {
		return cnpj;
	}
	public void setCnpj(Documento cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public TipoPessoa getTipo() {
		return TipoPessoa.JURIDICA;
	}
	
	@Override
	public Documento getDocumentoPrincipal() {
		return this.cnpj;
	}

	@Override
	public String getNome() {
		return this.getNomeFantasia();
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
}
