package br.com.assembliescorp.services;

import java.util.List;

import br.com.assembliescorp.domain.RulingCreateDTO;
import br.com.assembliescorp.domain.RulingListDTO;

public interface RulingService {
	
	List<RulingListDTO> getList();	
	RulingCreateDTO create(RulingCreateDTO rulingCreateDTO);
}
