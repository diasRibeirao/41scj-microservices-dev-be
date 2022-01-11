package br.com.fiap.microservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.microservices.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Transactional(readOnly = true)
	Optional<Role> findByNome(String nome);
	
}
