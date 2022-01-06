package br.com.fiap.microservices.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.fiap.microservices.entities.dto.VeiculoDTO;

@Component
@FeignClient(name = "veiculos-service")
public interface VeiculosFeignClients {

	@GetMapping(value = "/veiculos-service/parceiro/{parceiroId}")
	ResponseEntity<List<VeiculoDTO>> findByParceiroId(@PathVariable Long parceiroId);
}
