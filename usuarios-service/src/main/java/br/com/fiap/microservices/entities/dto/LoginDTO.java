package br.com.fiap.microservices.entities.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
	@NotEmpty(message = "O telefone deve ser informado")
	private String login;
	
	@NotEmpty(message = "A senha deve ser informada")
	private String senha;
	
	@NotEmpty(message = "A role deve ser informada")
	private String role;
}