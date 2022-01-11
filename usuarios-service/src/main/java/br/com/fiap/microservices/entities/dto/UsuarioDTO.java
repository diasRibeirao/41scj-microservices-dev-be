package br.com.fiap.microservices.entities.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {

	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private String login;
	private String telefone;
	private Integer situacao;
	private Set<RoleDTO> roles = new HashSet<RoleDTO>();

}
