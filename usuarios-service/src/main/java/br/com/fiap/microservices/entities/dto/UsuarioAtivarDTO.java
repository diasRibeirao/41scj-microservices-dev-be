package br.com.fiap.microservices.entities.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsuarioAtivarDTO {

	@NotEmpty(message = "Preenchimento obrigatório")
	@Size(min = 10, max = 11, message = "O telefone deve possuir {max} caracteres")
	private String telefone;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Size(min = 4, max = 4, message = "O código deve possuir {max} caracteres")
	private String codigo;

}
