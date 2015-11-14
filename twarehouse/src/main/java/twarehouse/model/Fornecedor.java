package twarehouse.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import twarehouse.converter.LocalDateDBConverter;

@Entity
@Vetoed
@Table(name="fornecedor")
public class Fornecedor implements Serializable {
	
	private static final long serialVersionUID = 2940168056236844127L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(length=60)
	private String endereco;
	
	@Column(length=60)
	private String numero;
	
	@Column(length=60)
	private String bairro;
	
	@Column(length=60)
	private String cidade;
	
	@Column(length=2)
	private Estado estado;
	
	@Column(length=8)
	private String cep;
	
	@Column(length=60)
	private String site;
	
	@Column(length=60)
	private String email;
	
	@Column(length=60)
	private String contato;

	@OneToOne(
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL,
			orphanRemoval=true)
	@JoinColumn(name="pessoa_codigo")
	private Pessoa pessoa;
	
	@Column(columnDefinition="DATE", name="data_de_cadastro")
	@Convert(converter=LocalDateDBConverter.class)
	private LocalDate dataDeCadastro;
	
	@OneToMany(
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY,
			orphanRemoval=true)
	@CollectionTable(name="telefones_de_fornecedores")
	private List<Telefone> telefones;
	
	/**
	 * Construtor padrão que inicia a data de cadastro com 
	 * a data atual.
	 * 
	 */
	public Fornecedor() {	
		this.dataDeCadastro = LocalDate.now();
		
		this.telefones = new ArrayList<Telefone>();
	}
	
	/**
	 * @param codigo
	 */
	public Fornecedor(Long codigo) {
		this();
		this.codigo = codigo;
	}

	/**
	 * Formata o código com zeros a esquerda.
	 * 
	 * @return
	 */
	public String codigoFormatado() {
		
		if (null == this.codigo) {
			return "";
		}
		
		return String.format("%06d", this.codigo);
	}
	
	/**
	 * Adiciona um telefone a lista de telefones do Fornecedor.
	 * 
	 * @param telefone
	 */
	public void adicionaTelefone(Telefone telefone) {
		telefones.add(telefone);
	}

	/**
	 * Remove um telefone a lista de telefones do Fornecedor.
	 * 
	 * @param telefoneSelecionado
	 */
	public void removeTelefone(Telefone telefone) {
		telefones.remove(telefone);
	}
	
	/**
	 * Caso haja algum telefone cadastrado retorna o primeiro da lista, 
	 * considerando-o como o principal.
	 * 
	 * @return
	 */
	public Telefone telefonePrincipal(){
		if (null == telefones || telefones.size() == 0) {
			return null;
		}
		
		return telefones.get(0);
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public LocalDate getDataDeCadastro() {
		return dataDeCadastro;
	}
	public void setDataDeCadastro(LocalDate dataDeCadastro) {
		this.dataDeCadastro = dataDeCadastro;
	}
	
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
