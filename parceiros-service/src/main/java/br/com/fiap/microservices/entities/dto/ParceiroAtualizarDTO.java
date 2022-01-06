package br.com.fiap.microservices.entities.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.fiap.microservices.services.validation.ParceiroAtualizar;
import lombok.Data;

@ParceiroAtualizar
@Data
public class ParceiroAtualizarDTO {

	@NotEmpty(message = "Preenchimento obrigatório!")
	@Size(min = 2, max = 40, message = "O nome deve possuir entre {min} e {max} caracteres!")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório!")
	@Size(min = 2, max = 40, message = "O sobrenome deve possuir entre {min} e {max} caracteres!")
	private String sobrenome;

	@NotEmpty(message = "Preenchimento obrigatório!")
	@Size(min = 2, max = 120, message = "O email deve possuir entre {min} e {max} caracteres!")
	private String email;

	@NotEmpty(message = "Preenchimento obrigatório!")
	@Size(min = 10, max = 11, message = "O telefone deve possuir entre {min} e {max} caracteres!")
	private String telefone;

	@NotNull(message = "Preenchimento obrigatório!")
	private Integer situacao;

}
