package br.com.fiap.microservices.entities.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsuarioEsqueceuSenhaDTO {

	@Size(min = 10, max = 11, message = "O telefone deve possuir {max} caracteres")
	private String telefone;

}
