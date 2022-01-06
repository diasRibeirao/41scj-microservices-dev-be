package br.com.fiap.microservices.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import br.com.fiap.microservices.entities.Notificacao;
import br.com.fiap.microservices.repositories.NotificacoesRepository;

@Service
public class TwilioService {
	
	@Autowired
	private NotificacoesRepository repository;
	
	@Value("${twilio.account.sid}")
	private String twilioAccountSid;
	
	@Value("${twilio.auth.token}")
	private String twilioAuthToken;
	
	@Value("${twilio.account.phone}")
	private String twilioAccountPhone;

	public Notificacao enviarSms(Notificacao notificacao) {
		Twilio.init(this.twilioAccountSid, this.twilioAuthToken);
				
		Message message = Message.creator(
                new com.twilio.type.PhoneNumber(notificacao.getPara()),
                new com.twilio.type.PhoneNumber(this.twilioAccountPhone),
                notificacao.getMensagem())
            .create();

        System.out.println(message.getSid());
        notificacao.setObservacao(message.getBody());
        notificacao.setData(Calendar.getInstance().getTime());
        
        repository.save(notificacao);
        
        return notificacao;
	}

	
}
