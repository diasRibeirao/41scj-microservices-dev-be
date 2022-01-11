package br.com.fiap.microservices.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.fiap.microservices.entities.Usuario;
import br.com.fiap.microservices.entities.dto.UsuarioAtualizarDTO;
import br.com.fiap.microservices.repositories.UsuarioRepository;
import br.com.fiap.microservices.resources.exceptions.FieldMessage;

public class UsuarioAtualizarValidator implements ConstraintValidator<UsuarioAtualizar, UsuarioAtualizarDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UsuarioRepository repository;

	@Override
	public void initialize(UsuarioAtualizar ann) {
	}

	@Override
	public boolean isValid(UsuarioAtualizarDTO usuarioDTO, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		Optional<Usuario> aux = repository.findByTelefone(usuarioDTO.getTelefone());
		if (!aux.isEmpty() && !aux.get().getId().equals(uriId)) {
			list.add(new FieldMessage("telefone", "Telefone já cadastrado"));
		}

		aux = repository.findByEmail(usuarioDTO.getEmail());
		if (!aux.isEmpty() && !aux.get().getId().equals(uriId)) {
			list.add(new FieldMessage("email", "E-mail já cadastrado"));
		}
				
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}

		return list.isEmpty();
	}
}
