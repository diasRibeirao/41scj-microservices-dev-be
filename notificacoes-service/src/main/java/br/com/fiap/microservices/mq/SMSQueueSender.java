package br.com.fiap.microservices.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.microservices.entities.Notificacao;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SMSQueueSender {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private Queue smsQueue;

	public void send(Notificacao notificacao) {
		this.rabbitTemplate.convertAndSend(this.smsQueue.getName(), notificacao);
	}

}
