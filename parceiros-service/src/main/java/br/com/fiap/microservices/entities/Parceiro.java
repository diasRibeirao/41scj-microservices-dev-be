package br.com.fiap.microservices.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.fiap.microservices.entities.enums.SituacaoParceiro;
import lombok.Data;

@Entity
@Data
@Table(name = "parceiros")
public class Parceiro implements Serializable {
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
	private String telefone;

	private String senha;

	private Integer situacao;
	
	@Column(name = "codigo_ativar", nullable = true)
	private String codigoAtivar;
	
	@Column(name = "data_limite_ativar", nullable = true)
	private Date dataLimiteAtivar;

	public Parceiro() {

	}

	public Parceiro(String nome, String sobrenome, String email, String telefone, String senha,
			SituacaoParceiro situacao, String codigoAtivar, Date dataLimiteAtivar) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.telefone = telefone;
		this.senha = senha;
		this.situacao = (situacao == null) ? null : situacao.getId();
		this.codigoAtivar = codigoAtivar;
		this.dataLimiteAtivar = dataLimiteAtivar;
	}

	public Parceiro(Long id, String nome, String sobrenome, String email, String telefone, String senha,
			SituacaoParceiro situacao, String codigoAtivar, Date dataLimiteAtivar) {
		this(nome, sobrenome, email, telefone, senha, situacao, codigoAtivar, dataLimiteAtivar);
		this.id = id;
	}

}
