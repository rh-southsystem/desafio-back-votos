package br.com.assembliescorp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.assembliescorp.domain.dtos.associate.AssociateCreateDTO;
import br.com.assembliescorp.domain.dtos.associate.AssociateListDTO;

public interface AssociateService {
	
	List<AssociateListDTO> getList();	
	AssociateCreateDTO create(AssociateCreateDTO associateCreateDTO);

}
