package com.southsystem.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.southsystem.controller.exception.FieldMessage;
import com.southsystem.domain.Associate;
import com.southsystem.dto.AssociateUpdateDTO;
import com.southsystem.repository.AssociateRepository;

public class AssociateUpdateValidator implements ConstraintValidator<AssociateUpdate, AssociateUpdateDTO> {

	@Autowired
	private AssociateRepository associateRepository;
	
	@Override
	public void initialize(AssociateUpdate ann) {
	}

	@Override
	public boolean isValid(AssociateUpdateDTO associateUpdateDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		Optional<Associate> optionalAssociate = associateRepository.findByCpf(associateUpdateDTO.getCpf());
		
		if (optionalAssociate.isPresent() && !optionalAssociate.get().getId().equals(associateUpdateDTO.getId())) {
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
