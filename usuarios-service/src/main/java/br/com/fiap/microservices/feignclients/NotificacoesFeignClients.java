package br.com.fiap.microservices.feignclients;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.fiap.microservices.entities.dto.NotificacaoDTO;
import br.com.fiap.microservices.entities.dto.NotificacaoSendDTO;

@Component
@FeignClient(name = "notificacoes-service")
public interface NotificacoesFeignClients {

	@GetMapping(value = "/notificacoes/sms")
	public ResponseEntity<NotificacaoDTO> sms(@Valid @RequestBody NotificacaoSendDTO notificacaoSendDTO);
}
