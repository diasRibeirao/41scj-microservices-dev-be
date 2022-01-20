package br.com.fiap.microservices.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fiap.microservices.entities.Usuario;
import br.com.fiap.microservices.entities.dto.UsuarioAdicionarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtivarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtualizarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioDTO;
import br.com.fiap.microservices.entities.dto.UsuarioEsqueceuSenhaDTO;
import br.com.fiap.microservices.entities.dto.UsuarioOauthDTO;
import br.com.fiap.microservices.entities.dto.converter.UsuarioConverter;
import br.com.fiap.microservices.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API's Usuários")
@RestController
@RefreshScope
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioConverter converter;

	@Operation(summary = "Busca todos os usuários")
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<UsuarioDTO> list = converter.Parse(usuarioService.findAll());
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "Busca um usuário pelo seu ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
		UsuarioDTO obj = converter.Parse(usuarioService.find(id));
		return ResponseEntity.ok(obj);
	}
	
	@Operation(summary = "Busca um usuário pelo seu Login")
	@GetMapping(value = "/login/{login}")
	public ResponseEntity<UsuarioOauthDTO> findByLogin(@PathVariable String login) {
		UsuarioOauthDTO obj = converter.ParseUsuarioOauthDTO(usuarioService.findByLogin(login));
		return ResponseEntity.ok(obj);
	}

	@Operation(summary = "Cadastrar um novo usuário")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioAdicionarDTO usuarioAdicionarDTO) {
		Usuario usuario = converter.ParseAdicionarDTO(usuarioAdicionarDTO);
		usuario = usuarioService.insert(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@Operation(summary = "Atualizar um usuário")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioAtualizarDTO usuarioAtualizarDTO,
			@PathVariable Long id) {
		Usuario obj = converter.ParseAtualizarDTO(usuarioAtualizarDTO);
		obj.setId(id);
		obj = usuarioService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Ativar um novo usuário")
	@PostMapping(value = "/ativar")
	public ResponseEntity<Void> ativar(@Valid @RequestBody UsuarioAtivarDTO usuarioAtivarDTO) {
		usuarioService.ativar(usuarioAtivarDTO);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Esqueceu a senha")
	@PostMapping(value = "/esqueceu-senha")
	public ResponseEntity<Void> esqueceuSenha(@Valid @RequestBody UsuarioEsqueceuSenhaDTO usuarioEsqueceuSenhaDTO) {
		usuarioService.esqueceuSenha(usuarioEsqueceuSenhaDTO);
		return ResponseEntity.noContent().build();
	}

}
