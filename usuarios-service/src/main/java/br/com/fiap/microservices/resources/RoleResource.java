package br.com.fiap.microservices.resources;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.microservices.entities.dto.RoleDTO;
import br.com.fiap.microservices.entities.dto.converter.RoleConverter;
import br.com.fiap.microservices.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API's Roles")
@RestController
@RefreshScope
@RequestMapping(value = "/usuarios")
public class RoleResource {

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleConverter converter;

	@Operation(summary = "Busca todos as roles")
	@GetMapping("/roles")
	public ResponseEntity<Set<RoleDTO>> findAllRoles() {
		Set<RoleDTO> list = converter.Parse(roleService.findAll());
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "Busca um usuário pelo seu NOME")
	@GetMapping(value = "/roles/nome/{nome}")
	public ResponseEntity<RoleDTO> findByNomeRole(@PathVariable String nome) {
		RoleDTO obj = converter.Parse(roleService.findByNome(nome));
		return ResponseEntity.ok(obj);
	}
	
}
