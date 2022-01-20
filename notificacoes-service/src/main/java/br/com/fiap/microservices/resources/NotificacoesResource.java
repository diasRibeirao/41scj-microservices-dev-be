package br.com.fiap.microservices.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.microservices.entities.Notificacao;
import br.com.fiap.microservices.entities.dto.NotificacaoSendDTO;
import br.com.fiap.microservices.entities.dto.converter.NotificacaoConverter;
import br.com.fiap.microservices.services.TwilioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API's Notificações")
@RestController
@RefreshScope
@RequestMapping(value = "/notificacoes")
public class NotificacoesResource {

	@Autowired
	private TwilioService service;
	
	@Autowired
	private NotificacaoConverter converter;

	@Operation(summary = "Enviar mensagem por SMS")
	@PostMapping(value = "/sms")
	public ResponseEntity<Notificacao> sms(@Valid @RequestBody NotificacaoSendDTO notificacaoSendDTO) {
		Notificacao notificacao = converter.ParseDTO(notificacaoSendDTO);
		notificacao = service.enviarSms(notificacao);
		return ResponseEntity.ok(notificacao);
	}

}
