package br.com.fiap.microservices.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.microservices.entities.Veiculo;

public interface VeiculosRepository extends JpaRepository<Veiculo, Long> {

	@Transactional(readOnly = true)
	List<Veiculo> findByParceiroId(Long parceiroId);

}
