package br.com.fiap.microservices.entities.dto;

import lombok.Data;

@Data
public class UsuarioNovaSenhaDTO {

	private String senha;
	private String confirmarSenha;
	
}
