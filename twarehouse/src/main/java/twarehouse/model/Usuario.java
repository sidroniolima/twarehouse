package twarehouse.model;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Vetoed
@Table(name="usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(length=60)
	private String nome;
	
	@Column(length=60)
	private String email;
	
	@Column(length=15)
	private String senha;

	@CollectionTable(name="usuario_permissoes")
	@JoinTable(joinColumns= { @JoinColumn(name="usuario_codigo")})
	@ElementCollection(targetClass=Permissao.class, fetch=FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@Column(name="permissao_codigo")
	private List<Permissao> permissoes = new ArrayList<Permissao>();
	
	@Column(name="tenant_id", unique=true)
	private String tentantId;
	
	public Usuario() {	}
	
	/**
	 * @param email2
	 * @param senha2
	 */
	public Usuario(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public boolean temPermissoes() {
		return permissoes.size() > 0;
	}
	
	/**
	 * Indica se o usuário é ou não administrado.
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		if (null == permissoes) {
			return false;
		}
		
		for (Permissao permissao : permissoes) {
			if (permissao.equals(Permissao.USER_ADMIN)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Código formatado com 3 caracteres.
	 * 
	 * @return
	 */
	public String codigoFormatado() {
		if (null == codigo) {
			return "";
		}
		
		return String.format("%06d", codigo);
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public String getTentantId() {
		return tentantId;
	}
	public void setTentantId(String tentantId) {
		this.tentantId = tentantId;
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
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
