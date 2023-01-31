package br.com.assembliescorp.services;

import java.util.List;
import java.util.Optional;

import br.com.assembliescorp.domain.dtos.ruling.RulingCreateDTO;
import br.com.assembliescorp.domain.dtos.ruling.RulingListDTO;
import br.com.assembliescorp.domain.entities.RulingEntity;


public interface RulingService {
	
	List<RulingListDTO> getList();	
	RulingCreateDTO create(RulingCreateDTO rulingCreateDTO);
	Optional<RulingEntity> findOne(Long idRuling);
}
