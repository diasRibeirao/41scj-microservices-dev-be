package br.com.fiap.microservices.entities.dto.converter;

import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Notificacao;
import br.com.fiap.microservices.entities.dto.NotificacaoSendDTO;

@Service
public class NotificacaoConverter {

	public Notificacao ParseDTO(NotificacaoSendDTO origin) {
		if (origin == null)
			return null;

		return new Notificacao(origin.getPara(), origin.getMensagem());
	}
	
}
