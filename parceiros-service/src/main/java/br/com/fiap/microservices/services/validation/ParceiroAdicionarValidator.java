package br.com.fiap.microservices.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.microservices.entities.Parceiro;
import br.com.fiap.microservices.entities.dto.ParceiroAdicionarDTO;
import br.com.fiap.microservices.repositories.ParceirosRepository;
import br.com.fiap.microservices.resources.exceptions.FieldMessage;

public class ParceiroAdicionarValidator implements ConstraintValidator<ParceiroAdicionar, ParceiroAdicionarDTO> {

	@Autowired
	private ParceirosRepository repository;

	@Override
	public void initialize(ParceiroAdicionar ann) {
	}

	@Override
	public boolean isValid(ParceiroAdicionarDTO parceiroAdicionarDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Optional<Parceiro> aux = repository.findByTelefone(parceiroAdicionarDTO.getTelefone());
		if (!aux.isEmpty()) {
			list.add(new FieldMessage("telefone", "Telefone já cadastrado"));
		}

		aux = repository.findByEmail(parceiroAdicionarDTO.getEmail());
		if (!aux.isEmpty()) {
			list.add(new FieldMessage("email", "E-mail já cadastrado"));
		}
		
		if (!parceiroAdicionarDTO.getSenha().equals(parceiroAdicionarDTO.getConfirmarSenha())) {
			list.add(new FieldMessage("senha", "As senhas não conferem"));
			list.add(new FieldMessage("confirmarSenha", "As senhas não conferem"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}

		return list.isEmpty();
	}
}
