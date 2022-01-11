package br.com.fiap.microservices.entities.enums;

public enum SituacaoUsuario {
	
	AG_ATIVACAO(1, "Aguardando ativação"), 
	ATIVO(2, "Ativo"),
	AG_PAGAMENTO(3, "Aguardando pagamento");

	private Integer id;
	private String description;

	private SituacaoUsuario(Integer id, String descricao) {
		this.id = id;
		this.description = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static SituacaoUsuario toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (SituacaoUsuario situacaoUsuario : SituacaoUsuario.values()) {
			if (id.equals(situacaoUsuario.getId())) {
				return situacaoUsuario;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + id);
	}
}
