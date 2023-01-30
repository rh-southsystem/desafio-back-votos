package br.com.assembliescorp.services;

import java.util.List;
import java.util.Optional;

import br.com.assembliescorp.domain.dtos.associate.AssociateCreateDTO;
import br.com.assembliescorp.domain.dtos.associate.AssociateListDTO;
import br.com.assembliescorp.domain.entities.AssociateEntity;

public interface AssociateService {
	
	List<AssociateListDTO> getList();	
	AssociateCreateDTO create(AssociateCreateDTO associateCreateDTO);
	Optional<AssociateEntity> findOne(Long idAssociate);

}
