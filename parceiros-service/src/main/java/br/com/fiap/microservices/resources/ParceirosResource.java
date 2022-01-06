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

import br.com.fiap.microservices.entities.Parceiro;
import br.com.fiap.microservices.entities.dto.ParceiroAdicionarDTO;
import br.com.fiap.microservices.entities.dto.ParceiroAtivarDTO;
import br.com.fiap.microservices.entities.dto.ParceiroAtualizarDTO;
import br.com.fiap.microservices.entities.dto.ParceiroDTO;
import br.com.fiap.microservices.entities.dto.VeiculoDTO;
import br.com.fiap.microservices.entities.dto.converter.ParceiroConverter;
import br.com.fiap.microservices.services.ParceirosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API's Parceiros")
@RestController
@RefreshScope
@RequestMapping(value = "/parceiros-service")
public class ParceirosResource {

	@Autowired
	private ParceirosService service;

	@Autowired
	private ParceiroConverter converter;

	@Operation(summary = "Busca todos os parceiros")
	@GetMapping
	public ResponseEntity<List<ParceiroDTO>> findAll() {
		List<ParceiroDTO> list = converter.Parse(service.findAll());
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "Busca um parceiro pelo seu ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ParceiroDTO> findById(@PathVariable Long id) {
		ParceiroDTO obj = converter.Parse(service.find(id));
		return ResponseEntity.ok(obj);
	}

	@Operation(summary = "Busca os veículos de um parceiro pelo seu ID")
	@GetMapping(value = "/{id}/veiculos")
	public ResponseEntity<List<VeiculoDTO>> findVeiculosById(@PathVariable Long id) {
		List<VeiculoDTO> list = service.findByParceiroId(id);
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "Cadastrar um novo parceiro")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ParceiroAdicionarDTO parceiroAdicionarDTO) {
		Parceiro parceiro = converter.ParseAdicionarDTO(parceiroAdicionarDTO);
		parceiro = service.insert(parceiro);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(parceiro.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@Operation(summary = "Atualizar um aluno")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ParceiroAtualizarDTO parceiroAtualizarDTO, @PathVariable Long id) {
		Parceiro obj = converter.ParseAtualizarDTO(parceiroAtualizarDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Ativar um novo parceiro")
	@PostMapping(value = "/ativar")
	public ResponseEntity<Void> ativar(@Valid @RequestBody ParceiroAtivarDTO parceiroAtivarDTO) {
		service.ativar(parceiroAtivarDTO);
		return ResponseEntity.noContent().build();
	}
}
