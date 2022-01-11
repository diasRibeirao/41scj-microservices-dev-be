package br.com.fiap.microservices.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.microservices.entities.Usuario;
import br.com.fiap.microservices.entities.dto.UsuarioAdicionarDTO;
import br.com.fiap.microservices.repositories.UsuarioRepository;
import br.com.fiap.microservices.resources.exceptions.FieldMessage;

public class UsuarioAdicionarValidator implements ConstraintValidator<UsuarioAdicionar, UsuarioAdicionarDTO> {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public void initialize(UsuarioAdicionar ann) {
	}

	@Override
	public boolean isValid(UsuarioAdicionarDTO usuarioDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Optional<Usuario> aux = repository.findByTelefone(usuarioDTO.getTelefone());
		if (!aux.isEmpty()) {
			list.add(new FieldMessage("telefone", "Telefone já cadastrado"));
		}

		aux = repository.findByEmail(usuarioDTO.getEmail());
		if (!aux.isEmpty()) {
			list.add(new FieldMessage("email", "E-mail já cadastrado"));
		}

		if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmarSenha())) {
			list.add(new FieldMessage("senha", "As senhas não conferem"));
			list.add(new FieldMessage("confirmarSenha", "As senhas não conferem"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}
