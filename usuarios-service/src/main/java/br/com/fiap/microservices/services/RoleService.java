package br.com.fiap.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Role;
import br.com.fiap.microservices.repositories.RoleRepository;
import br.com.fiap.microservices.services.exceptions.ObjectNotFoundException;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;


	public List<Role> findAll() {
		return repository.findAll();
	}

	public Role find(Long id) {
		Optional<Role> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Role.class.getName()));
	}

	public Role findByNome(String nome) {
		Optional<Role> obj = repository.findByNome(nome);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Telefone: " + nome + ", Tipo: " + Role.class.getName()));
	}

	

}
