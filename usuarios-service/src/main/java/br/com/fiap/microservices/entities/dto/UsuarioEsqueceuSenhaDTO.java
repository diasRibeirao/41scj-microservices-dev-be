package br.com.fiap.microservices.entities.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsuarioEsqueceuSenhaDTO {

	@Size(min = 15, max = 15, message = "O telefone deve possuir {max} caracteres")
	private String telefone;

}
