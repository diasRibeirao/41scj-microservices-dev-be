package br.com.fiap.microservices.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDTO {
	private Long id;
	private String nome;
}
