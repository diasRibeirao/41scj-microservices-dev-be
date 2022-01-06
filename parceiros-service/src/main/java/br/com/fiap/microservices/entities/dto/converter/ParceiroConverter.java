package br.com.fiap.microservices.entities.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Parceiro;
import br.com.fiap.microservices.entities.dto.ParceiroAdicionarDTO;
import br.com.fiap.microservices.entities.dto.ParceiroAtualizarDTO;
import br.com.fiap.microservices.entities.dto.ParceiroDTO;
import br.com.fiap.microservices.entities.enums.SituacaoParceiro;

@Service
public class ParceiroConverter {

	public ParceiroDTO Parse(Parceiro origin) {
		if (origin == null)
			return null;

		return new ParceiroDTO(origin.getId(), origin.getNome(), origin.getSobrenome(), origin.getEmail(),
				origin.getTelefone(), origin.getSituacao());

	}

	public List<ParceiroDTO> Parse(List<Parceiro> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toList());
	}

	// Adicionar
	public Parceiro ParseAdicionarDTO(ParceiroAdicionarDTO origin) {
		if (origin == null)
			return null;

		return new Parceiro(origin.getNome(), origin.getSobrenome(), origin.getEmail(), origin.getTelefone(),
				origin.getSenha(), SituacaoParceiro.AG_ATIVACAO, null, null);
	}

	public List<Parceiro> ParseAdicionarDTO(List<ParceiroAdicionarDTO> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> ParseAdicionarDTO(obj)).collect(Collectors.toList());
	}
	
	// Atualizar
	public Parceiro ParseAtualizarDTO(ParceiroAtualizarDTO origin) {
		if (origin == null)
			return null;

		return new Parceiro(origin.getNome(), origin.getSobrenome(), origin.getEmail(), origin.getTelefone(),
				null, SituacaoParceiro.toEnum(origin.getSituacao()), null, null);
	}

	public List<Parceiro> ParseAtualizarDTO(List<ParceiroAtualizarDTO> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> ParseAtualizarDTO(obj)).collect(Collectors.toList());
	}
	
}
