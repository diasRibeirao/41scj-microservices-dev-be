package br.com.fiap.microservices.entities.dto;

import java.util.Date;

import lombok.Data;

@Data
public class NotificacaoDTO {
	private Long id;
	private String para;
	private String mensagem;
	private String observacao;
	private Date data;
}
