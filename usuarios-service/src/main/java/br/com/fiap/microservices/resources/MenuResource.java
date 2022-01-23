package br.com.fiap.microservices.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.microservices.entities.dto.MenuDTO;
import br.com.fiap.microservices.entities.dto.converter.MenuConverter;
import br.com.fiap.microservices.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API's Menus")
@RestController
@RefreshScope
@RequestMapping(value = "/usuarios")
public class MenuResource {

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuConverter converter;

	@Operation(summary = "Busca o menu do usuário pela sua ROLE")
	@GetMapping(value = "/menus/role/{role}")
	public ResponseEntity<List<MenuDTO>> findByRole(@PathVariable String role) {
		List<MenuDTO> list = converter.Parse(menuService.findByRole(role));
		return ResponseEntity.ok(list);
	}

}
