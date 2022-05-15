package com.southsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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

	public Associate create(AssociateCreateDTO associateCreateDTO) {
		Associate associate = modelMapper.map(associateCreateDTO, Associate.class);
		
		_validateCPF(associate);
		associate.setCreationDate(LocalDateTime.now());
		return associateRepository.save(associate);
	}

	public Associate findById(Integer id) {
		Optional<Associate> optionalAssociate = associateRepository.findById(id);
		if (optionalAssociate.isEmpty()) {
			throw new EntityNotFoundException();
		}

		return optionalAssociate.get();
	}

	public Page<AssociateReadDTO> list(Integer page, Integer linesPerPage, String orderBy, String direction,
			String name) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return associateRepository.findByNameContainsIgnoreCase(name, pageRequest)
				.map(associate -> modelMapper.map(associate, AssociateReadDTO.class));
	}

	public Associate update(AssociateUpdateDTO associateUpdateDTO) {
		Associate associate = findById(associateUpdateDTO.getId());
		associate.setCpf(associateUpdateDTO.getCpf());
		associate.setName(associateUpdateDTO.getName());
		associate.setUpdateDate(LocalDateTime.now());
		_validateCPF(associate);
		return associateRepository.save(associate);
	}

	public void delete(Integer id) {
		findById(id);
		associateRepository.deleteById(id);
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
