package br.com.fiap.microservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.microservices.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Transactional(readOnly = true)
	Optional<Usuario> findByEmail(String email);
	
	@Transactional(readOnly = true)
	Optional<Usuario> findByLogin(String login);
	
	@Transactional(readOnly = true)
	Optional<Usuario> findByTelefone(String telefone);
}
