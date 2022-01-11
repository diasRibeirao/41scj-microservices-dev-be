package br.com.fiap.microservices.entities.dto;

import lombok.Data;

@Data
public class NotificacaoSendDTO {

	private String para;
	private String mensagem;

	public NotificacaoSendDTO(String para, String mensagem) {
		this.para = para;
		this.mensagem = mensagem;
	}

}
