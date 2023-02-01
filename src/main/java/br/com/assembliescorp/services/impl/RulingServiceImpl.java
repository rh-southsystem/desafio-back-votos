package br.com.assembliescorp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.dtos.ruling.RulingCreateDTO;
import br.com.assembliescorp.domain.dtos.ruling.RulingListDTO;
import br.com.assembliescorp.domain.entities.RulingEntity;
import br.com.assembliescorp.domain.repositories.RulingRepository;
import br.com.assembliescorp.services.RulingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		var ruling = new RulingEntity(rulingCreateDTO);
		rulingRepository.save(ruling);
		log.info("PAUTA CADASTRADO COM SUCESSO : {}", rulingCreateDTO.name());
		return new RulingCreateDTO(ruling);
	}

	public Optional<RulingEntity> findOne(Long idRuling) {
		return rulingRepository.findById(idRuling);
	}	

}
	