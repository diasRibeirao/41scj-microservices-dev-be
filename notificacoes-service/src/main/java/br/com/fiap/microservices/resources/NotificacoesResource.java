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
import br.com.fiap.microservices.mq.SMSQueueSender;
import br.com.fiap.microservices.services.NotificacoesService;
import br.com.fiap.microservices.services.SlackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API's Notificações")
@RestController
@RefreshScope
@RequestMapping(value = "/notificacoes")
public class NotificacoesResource {

	@Autowired
	private NotificacoesService notificacoesService;

	@Autowired
	private SlackService slackService;

	@Autowired
	private NotificacaoConverter converter;

	@Autowired
	private SMSQueueSender smsQueueSender;

	@Operation(summary = "Enviar mensagem por SMS")
	@PostMapping(value = "/sms")
	public ResponseEntity<Notificacao> sms(@Valid @RequestBody NotificacaoSendDTO notificacaoSendDTO) {
		Notificacao notificacao = converter.ParseDTO(notificacaoSendDTO);
		notificacao = notificacoesService.insert(notificacao);
		this.smsQueueSender.send(notificacao);
		return ResponseEntity.ok(notificacao);
	}

	@Operation(summary = "Enviar mensagem para SLACK")
	@PostMapping(value = "/slack")
	public ResponseEntity<Void> slack(@RequestBody String mensagem) {
		slackService.send(mensagem);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Teste")
	@PostMapping(value = "/teste2")
	public ResponseEntity<Void> teste2() {
		System.out.println("teste >>>>>>>>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.noContent().build();
	}
}
