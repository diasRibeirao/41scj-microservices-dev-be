package br.com.fiap.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Notificacao;
import br.com.fiap.microservices.repositories.NotificacoesRepository;
import br.com.fiap.microservices.services.exceptions.ObjectNotFoundException;

@Service
public class NotificacoesService {

	@Autowired
	private NotificacoesRepository repository;
	
	public List<Notificacao> findAll() {
		return repository.findAll();
	}

	public Notificacao find(Long id) {
		Optional<Notificacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Notificação não encontrada"));
	}

	public Notificacao insert(Notificacao notificacao) {
		notificacao.setId(null);
		return repository.save(notificacao);
	}
	
	public Notificacao update(Notificacao notificacao) {
		Notificacao notificacaoUpdate = find(notificacao.getId());
		updateData(notificacaoUpdate, notificacao);
		return repository.save(notificacaoUpdate);
	}

	private void updateData(Notificacao notificacaoUpdate, Notificacao notificacao) {
		// alterações aqui
	}
}
