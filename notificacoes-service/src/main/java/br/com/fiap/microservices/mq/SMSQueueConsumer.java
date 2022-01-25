package br.com.fiap.microservices.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.fiap.microservices.entities.Notificacao;
import br.com.fiap.microservices.services.TwilioService;

@Component
public class SMSQueueConsumer {
	
	@Autowired
	private TwilioService service;

	@RabbitListener(queues = { "${queue.sms.name}" })
	public void receive(@Payload Notificacao notificacao) {
		service.enviarSms(notificacao);
	}

}
