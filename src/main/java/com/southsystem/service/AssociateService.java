package com.southsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.southsystem.domain.Associate;
import com.southsystem.domain.enums.AssociatePermission;
import com.southsystem.dto.AssociateCreateDTO;
import com.southsystem.dto.AssociateReadDTO;
import com.southsystem.dto.AssociateUpdateDTO;
import com.southsystem.repository.AssociateRepository;
import com.southsystem.service.exception.EntityNotFoundException;

@Service
public class AssociateService {

	@Autowired
	private AssociateRepository associateRepository;

	@Autowired
	private CPFValidatorService cpfValidatorService;

	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AssociateService.class);

	public Associate create(AssociateCreateDTO associateCreateDTO) {
		LOGGER.info("Creating associate...");
		Associate associate = modelMapper.map(associateCreateDTO, Associate.class);
		
		_validateCPF(associate);
		associate.setCreationDate(LocalDateTime.now());
		associate = associateRepository.save(associate);
		
		LOGGER.info("Associate created successfully. Returning value");
		return associate;
	}

	public Associate findById(Integer id) {
		LOGGER.info("Searching associate...");
		
		Optional<Associate> optionalAssociate = associateRepository.findById(id);
		if (optionalAssociate.isEmpty()) {
			LOGGER.error("Associate not found");
			throw new EntityNotFoundException();
		}
		
		LOGGER.info("Associate found. Returning object");
		return optionalAssociate.get();
	}

	public Page<AssociateReadDTO> list(Integer page, Integer linesPerPage, String orderBy, String direction,
			String name) {
		LOGGER.info("Listing associates...");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<AssociateReadDTO> associates = associateRepository.findByNameContainsIgnoreCase(name, pageRequest)
				.map(associate -> modelMapper.map(associate, AssociateReadDTO.class));
		
		LOGGER.info("Listing done. Returning value");
		return associates;
	}

	public Associate update(AssociateUpdateDTO associateUpdateDTO) {
		LOGGER.info("Updating associate...");
		Associate associate = findById(associateUpdateDTO.getId());
		associate.setCpf(associateUpdateDTO.getCpf());
		associate.setName(associateUpdateDTO.getName());
		associate.setUpdateDate(LocalDateTime.now());
		
		_validateCPF(associate);
		associate = associateRepository.save(associate);
		
		LOGGER.info("Associate updated successfully. Returning value");
		return associate;
	}

	public void delete(Integer id) {
		findById(id);
		LOGGER.info("Deleting associate...");
		associateRepository.deleteById(id);
		LOGGER.info("Associate deleted successfully");
	}
	
	private void _validateCPF(Associate associate) {
		Integer statusCode = cpfValidatorService.callApiValidation(associate.getCpf());
		if (statusCode == HttpStatus.OK.value()) {
			associate.setPermission(AssociatePermission.ABLE_TO_VOTE.getId());
		} else {
			associate.setPermission(AssociatePermission.UNABLE_TO_VOTE.getId());
		}
	}

}
