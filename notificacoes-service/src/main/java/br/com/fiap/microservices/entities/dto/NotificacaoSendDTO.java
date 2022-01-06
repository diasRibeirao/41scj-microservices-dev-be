package br.com.fiap.microservices.entities.dto;

import lombok.Data;

@Data
public class NotificacaoSendDTO {

	private String para;
	private String mensagem;

}
