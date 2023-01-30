package br.com.assembliescorp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.RulingCreateDTO;
import br.com.assembliescorp.domain.RulingListDTO;
import br.com.assembliescorp.domain.repositories.RulingRepository;
import br.com.assembliescorp.services.RulingService;

@Service
public class RulingServiceImpl implements RulingService {
	
	public final RulingRepository rulingRepository;
	
	public RulingServiceImpl(RulingRepository rulingRepository) {
		this.rulingRepository = rulingRepository;
	}

	public List<RulingListDTO> getList() {
		return rulingRepository.findAll().stream().map(RulingListDTO::new).toList();
	}

	public RulingCreateDTO create(RulingCreateDTO rulingCreateDTO) {
		// TODO Auto-generated method stub
		return null;
	}	

}
	