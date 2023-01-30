package br.com.assembliescorp.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.dtos.associate.AssociateCreateDTO;
import br.com.assembliescorp.domain.dtos.associate.AssociateListDTO;
import br.com.assembliescorp.domain.entities.AssociateEntity;
import br.com.assembliescorp.domain.repositories.AssociateRepository;
import br.com.assembliescorp.services.AssociateService;

@Service
public class AssociateServiceImpl implements AssociateService {
	
	public final AssociateRepository associateRepository;
	
	public AssociateServiceImpl(AssociateRepository associateRepository) {
		this.associateRepository = associateRepository;
	}
	
	public AssociateCreateDTO create(AssociateCreateDTO associateCreateDTO){
		var associate = new AssociateEntity(associateCreateDTO);
		associateRepository.save(associate);
		return new AssociateCreateDTO(associate);		
	}

	public List<AssociateListDTO> getList() {
		return associateRepository.findAll().stream().map(AssociateListDTO::new).toList(); 	
	}

}
