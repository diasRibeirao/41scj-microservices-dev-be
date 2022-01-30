package br.com.fiap.microservices.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;

@Service
public class SlackService {

	@Value("${slack.bot.token}")
	private String slackBotToken;

	@Value("${slack.bot.channel}")
	private String slackBotChannel;

	public void send(String mensagem) {
		try {
			Slack.getInstance().methods()
					.chatPostMessage(r -> r.token(this.slackBotToken).channel(this.slackBotChannel).text(mensagem));
		} catch (Exception e) {
			System.out.println("Erro. " + e.getMessage());
		}
	}

}
