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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import twarehouse.converter.LocalDateDBConverter;

@Entity
@Vetoed
@Table(name="funcionario")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 9066399828446074297L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(length=6)
	private String matricula;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_setor")
	private Setor setor;
	
	@Column(length=60)
	private String nome;
	
	@Column(columnDefinition="DATE", name="data_de_admissao")
	@Convert(converter=LocalDateDBConverter.class)
	private LocalDate dataDeAdmissao;
	
	@OneToMany(
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY,
			orphanRemoval=true)
	@CollectionTable(name="telefones_de_funcionario")
	private List<Telefone> telefones;
	
	@Column(length=60)
	private String email;
	
	@Column(columnDefinition = "boolean default true")
	private boolean ativo;
	
	/**
	 * Construtor padrão.
	 */
	public Funcionario() {	
		ativo = true;
		telefones = new ArrayList<Telefone>();
	}
	
	/**
	 * Constrói um Funcionário com o Código.
	 * 
	 * @param codigo
	 */
	public Funcionario(Long codigo) {
		this();
		this.codigo = codigo;
	}
	
	/**
	 * Adiciona um telefone ao Funcionário.
	 * 
	 * @param telefone
	 */
	public void adicionaTelefone(Telefone telefone) {
		this.telefones.add(telefone);
	}

	/**
	 * Remove um da lista de telefones.
	 * 
	 * @param telefone
	 */
	public void removeTelefone(Telefone telefone) {
		this.telefones.remove(telefone);
	}	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor departamento) {
		this.setor = departamento;
	}
	
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getDataDeAdmissao() {
		return dataDeAdmissao;
	}
	public void setDataDeAdmissao(LocalDate dataDeAdmissao) {
		this.dataDeAdmissao = dataDeAdmissao;
	}

	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Transient
	public String getNomeESetor() {
		return
			this.getNome() + " (" +
			this.getSetor().getNome() + ")";
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
		Funcionario other = (Funcionario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
