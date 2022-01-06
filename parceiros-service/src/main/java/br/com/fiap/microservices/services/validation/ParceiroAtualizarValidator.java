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

import br.com.fiap.microservices.entities.Parceiro;
import br.com.fiap.microservices.entities.dto.ParceiroAtualizarDTO;
import br.com.fiap.microservices.repositories.ParceirosRepository;
import br.com.fiap.microservices.resources.exceptions.FieldMessage;

public class ParceiroAtualizarValidator implements ConstraintValidator<ParceiroAtualizar, ParceiroAtualizarDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ParceirosRepository repository;

	@Override
	public void initialize(ParceiroAtualizar ann) {
	}

	@Override
	public boolean isValid(ParceiroAtualizarDTO parceiroAtualizarDTO, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		Optional<Parceiro> aux = repository.findByTelefone(parceiroAtualizarDTO.getTelefone());
		if (!aux.isEmpty() && !aux.get().getId().equals(uriId)) {
			list.add(new FieldMessage("telefone", "Telefone já cadastrado"));
		}

		aux = repository.findByEmail(parceiroAtualizarDTO.getEmail());
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
