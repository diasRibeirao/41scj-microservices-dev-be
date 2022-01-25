package br.com.fiap.microservices.configuracao;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitMQSenderConfig {

	@Value("${queue.sms.name}")
	private String smsQueue;

	@Bean
	public Queue queue() {
		return new Queue(smsQueue, true);
	}
}
