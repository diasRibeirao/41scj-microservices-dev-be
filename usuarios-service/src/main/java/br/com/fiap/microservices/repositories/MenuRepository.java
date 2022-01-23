package br.com.fiap.microservices.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.microservices.entities.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Menu obj INNER JOIN obj.roles a WHERE a.nome = :role ORDER BY obj.ordem")
	List<Menu> findByRole(@Param("role") String role);
	

}
