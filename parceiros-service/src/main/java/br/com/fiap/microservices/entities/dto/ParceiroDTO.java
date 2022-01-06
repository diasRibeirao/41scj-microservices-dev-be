package br.com.fiap.microservices.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParceiroDTO {

	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private String telefone;
	private Integer situacao;
	
}
