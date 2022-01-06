package br.com.fiap.microservices.entities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class VeiculoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String placa;
	private String marca;
	private String modelo;
	private String cor;
	private Long parceiroId;
	private String port;
}
