package br.com.fiap.microservices.entities.enums;

public enum Notificacao {
	
	NOVO_USUARIO(1, "Olá! Seu código de ativação na VanCerta é %s"), 
	ESQUECEU_SENHA(2, "Olá! Seu código para gerar uma nova senha na VanCerta é %s");

	private Integer id;
	private String descricao;

	private Notificacao(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Notificacao toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (Notificacao notificacao : Notificacao.values()) {
			if (id.equals(notificacao.getId())) {
				return notificacao;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + id);
	}
}
