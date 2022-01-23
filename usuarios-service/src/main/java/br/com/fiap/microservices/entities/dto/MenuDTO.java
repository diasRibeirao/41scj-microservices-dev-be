package br.com.fiap.microservices.entities.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuDTO {
	private Long id;
	private String nome;	
	private String descricao;	
	private String icone;	
	private String rota;	
	private Integer ordem;
	private Set<RoleDTO> roles = new HashSet<RoleDTO>();
	
	
}