package br.com.fiap.microservices.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "notificacoes")
public class Notificacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String para;

	private String mensagem;

	private String observacao;

	private Date data;

	public Notificacao() {

	}

	public Notificacao(String para, String mensagem) {
		this.para = para;
		this.mensagem = mensagem;
	}

}
