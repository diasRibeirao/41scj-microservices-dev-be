package br.com.fiap.microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.microservices.entities.Notificacao;

public interface NotificacoesRepository extends JpaRepository<Notificacao, Long> {

	
}
