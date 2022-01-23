package br.com.fiap.microservices.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Menu;
import br.com.fiap.microservices.repositories.MenuRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository repository;

	public List<Menu> findByRole(String role) {
		return repository.findByRole(role);
	}

}
