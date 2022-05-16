package com.southsystem.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.southsystem.controller.exception.FieldMessage;
import com.southsystem.domain.Associate;
import com.southsystem.dto.AssociateCreateDTO;
import com.southsystem.repository.AssociateRepository;

public class AssociateCreateValidator implements ConstraintValidator<AssociateCreate, AssociateCreateDTO> {

	@Autowired
	private AssociateRepository associateRepository;
	
	@Override
	public void initialize(AssociateCreate ann) {
	}

	@Override
	public boolean isValid(AssociateCreateDTO associateCreateDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		Optional<Associate> optionalAssociate = associateRepository.findByCpf(associateCreateDTO.getCpf());
		
		if (optionalAssociate.isPresent()) {
			list.add(new FieldMessage("cpf", "This CPF is already registered"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
