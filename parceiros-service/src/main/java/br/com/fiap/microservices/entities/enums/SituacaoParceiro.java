package br.com.fiap.microservices.entities.enums;


public enum SituacaoParceiro {
	AG_ATIVACAO(1, "Aguardando ativação"), 
	ATIVO(2, "Ativo"),
	AG_PAGAMENTO(3, "Aguardando pagamento");

	private Integer id;
	private String description;

	private SituacaoParceiro(Integer id, String descricao) {
		this.id = id;
		this.description = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static SituacaoParceiro toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (SituacaoParceiro situacaoCliente : SituacaoParceiro.values()) {
			if (id.equals(situacaoCliente.getId())) {
				return situacaoCliente;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + id);
	}
}
