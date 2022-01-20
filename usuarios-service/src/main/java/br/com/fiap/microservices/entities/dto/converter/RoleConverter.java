package br.com.fiap.microservices.entities.dto.converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Role;
import br.com.fiap.microservices.entities.dto.RoleDTO;

@Service
public class RoleConverter {

	public RoleDTO Parse(Role origin) {
		if (origin == null)
			return null;

		return new RoleDTO(origin.getId(), origin.getNome());

	}

	public Set<RoleDTO> Parse(List<Role> origin) {
		if (origin == null)
			return null; 

		return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toSet());
	}
	
	public Set<RoleDTO> Parse(Set<Role> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toSet());
	}

	// Adicionar
	public Role ParseAdicionarDTO(RoleDTO origin) {
		if (origin == null)
			return null;

		return new Role(origin.getId(), origin.getNome());
	}

	public Set<Role> ParseAdicionarDTO(Set<RoleDTO> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> ParseAdicionarDTO(obj)).collect(Collectors.toSet());
	}
}