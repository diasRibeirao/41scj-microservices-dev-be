package br.com.fiap.microservices.entities.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Menu;
import br.com.fiap.microservices.entities.dto.MenuDTO;

@Service
public class MenuConverter {

	@Autowired
	private RoleConverter roleConverter;

	public MenuDTO Parse(Menu origin) {
		if (origin == null)
			return null;

		return new MenuDTO(origin.getId(), origin.getNome(), origin.getDescricao(), origin.getIcone(), origin.getRota(),
				origin.getOrdem(), roleConverter.Parse(origin.getRoles()));

	}

	public List<MenuDTO> Parse(List<Menu> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toList());
	}

}