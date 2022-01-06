package br.com.fiap.microservices.resources;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.microservices.entities.Veiculo;
import br.com.fiap.microservices.repositories.VeiculosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API's Veículos")
@RestController
@RequestMapping(value = "/veiculos-service")
public class VeiculosResource {
	
	@Autowired
	private Environment environment;

	@Autowired
	private VeiculosRepository repository;

	@Operation(summary = "Busca todos os veículos")
	@GetMapping
	public ResponseEntity<List<Veiculo>> findAll() {
		List<Veiculo> list = repository.findAll();
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "Busca um veículo pelo seu ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Veiculo> findById(@PathVariable Long id) {
		Veiculo obj = repository.findById(id).get();
		return ResponseEntity.ok(obj);
	}

	@Operation(summary = "Busca os veículos de um parceiro pelo seu ID")
	@GetMapping(value = "/parceiro/{parceiroId}")
	public ResponseEntity<List<Veiculo>> findByParceiroId(@PathVariable Long parceiroId) {
		List<Veiculo> list = repository.findByParceiroId(parceiroId);
		// testar load balance /////////////////////////////////////
		var port = environment.getProperty("local.server.port");
		for (Veiculo v : list) {
			v.setPort(port);
		}
		////////////////////////////////////////////////////////////
		
		return ResponseEntity.ok(list);
	}
}
