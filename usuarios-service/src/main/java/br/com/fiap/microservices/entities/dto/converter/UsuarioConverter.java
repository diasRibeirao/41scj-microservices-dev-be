package br.com.fiap.microservices.entities.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Usuario;
import br.com.fiap.microservices.entities.dto.UsuarioAdicionarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtualizarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioDTO;
import br.com.fiap.microservices.entities.enums.SituacaoUsuario;
import br.com.fiap.microservices.utils.Utils;

@Service
public class UsuarioConverter {

	@Autowired
	private RoleConverter roleConverter;

	public UsuarioDTO Parse(Usuario origin) {
		if (origin == null)
			return null;

		return new UsuarioDTO(origin.getId(), origin.getNome(), origin.getSobrenome(), origin.getEmail(),
				origin.getLogin(), origin.getTelefone(), origin.getSituacao(), roleConverter.Parse(origin.getRoles()));

	}

	public List<UsuarioDTO> Parse(List<Usuario> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toList());
	}

	// Adicionar
	public Usuario ParseAdicionarDTO(UsuarioAdicionarDTO origin) {
		if (origin == null)
			return null;

		return new Usuario(origin.getNome(), origin.getSobrenome(), origin.getEmail(), Utils.removeMaskCelular(origin.getTelefone()),
				Utils.removeMaskCelular(origin.getTelefone()), origin.getSenha(), SituacaoUsuario.AG_ATIVACAO, null, null,
				roleConverter.ParseAdicionarDTO(origin.getRoles()));
	}

	public List<Usuario> ParseAdicionarDTO(List<UsuarioAdicionarDTO> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> ParseAdicionarDTO(obj)).collect(Collectors.toList());
	}

	// Atualizar
	public Usuario ParseAtualizarDTO(UsuarioAtualizarDTO origin) {
		if (origin == null)
			return null;

		return new Usuario(origin.getNome(), origin.getSobrenome(), origin.getEmail(), Utils.removeMaskCelular(origin.getTelefone()),
				Utils.removeMaskCelular(origin.getTelefone()), null, SituacaoUsuario.toEnum(origin.getSituacao()), null, null,
				roleConverter.ParseAdicionarDTO(origin.getRoles()));
	}

	public List<Usuario> ParseAtualizarDTO(List<UsuarioAtualizarDTO> origin) {
		if (origin == null)
			return null;

		return origin.stream().map(obj -> ParseAtualizarDTO(obj)).collect(Collectors.toList());
	}

}
