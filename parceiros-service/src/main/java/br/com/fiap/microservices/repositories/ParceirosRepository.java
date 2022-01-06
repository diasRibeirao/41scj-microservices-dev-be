package br.com.fiap.microservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.microservices.entities.Parceiro;

public interface ParceirosRepository extends JpaRepository<Parceiro, Long> {

	@Transactional(readOnly = true)
	Optional<Parceiro> findByEmail(String email);
	
	@Transactional(readOnly = true)
	Optional<Parceiro> findByTelefone(String telefone);
}
