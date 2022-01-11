package br.com.fiap.microservices.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.fiap.microservices.entities.enums.SituacaoUsuario;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(name = "sobre_nome", nullable = false)
	private String sobrenome;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String login;

	@Column(unique = true)
	private String telefone;

	private String senha;

	private Integer situacao;

	@Column(name = "codigo_ativar", nullable = true)
	private String codigoAtivar;

	@Column(name = "data_limite_ativar", nullable = true)
	private Date dataLimiteAtivar;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public Usuario() {

	}

	public Usuario(String nome, String sobrenome, String email, String login, String telefone, String senha,
			SituacaoUsuario situacao, String codigoAtivar, Date dataLimiteAtivar, Set<Role> roles) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.login = login;
		this.telefone = telefone;
		this.senha = senha;
		this.situacao = (situacao == null) ? null : situacao.getId();
		this.codigoAtivar = codigoAtivar;
		this.dataLimiteAtivar = dataLimiteAtivar;
		this.roles = roles;
	}

	public Usuario(Long id, String nome, String sobrenome, String email, String login, String telefone, String senha,
			SituacaoUsuario situacao, String codigoAtivar, Date dataLimiteAtivar, Set<Role> roles) {
		this(nome, sobrenome, email, login, telefone, senha, situacao, codigoAtivar, dataLimiteAtivar, roles);
		this.id = id;
	}

}
