package br.com.assembliescorp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.clients.CpfValidation;
import br.com.assembliescorp.domain.dtos.associate.AssociateCreateDTO;
import br.com.assembliescorp.domain.dtos.associate.AssociateListDTO;
import br.com.assembliescorp.domain.entities.AssociateEntity;
import br.com.assembliescorp.domain.repositories.AssociateRepository;
import br.com.assembliescorp.services.AssociateService;
import jakarta.transaction.Transactional;

@Service
public class AssociateServiceImpl implements AssociateService {
	
	private final AssociateRepository associateRepository;
	private final CpfValidation cpfValidation;
	
	public AssociateServiceImpl(AssociateRepository associateRepository, CpfValidation cpfValidation) {
		this.associateRepository = associateRepository;
		this.cpfValidation = cpfValidation;
	}
	
	@Transactional
	public AssociateCreateDTO create(AssociateCreateDTO associateCreateDTO){
		
//		Validação do CPF, conforme tarefa 1
//		String retorno = cpfValidation.getValidationCpf(associateCreateDTO.cpf());
//		if(retorno.contains("UNABLE_TO_VOTE")) {
//			throw new UnableToVoteException();
//		}
		
		var associate = new AssociateEntity(associateCreateDTO);
		associateRepository.save(associate);
		return new AssociateCreateDTO(associate);		
	}

	public List<AssociateListDTO> getList() {
		return associateRepository.findAll().stream().map(AssociateListDTO::new).toList(); 	
	}

	public Optional<AssociateEntity> findOne(Long idAssociate) {
		return associateRepository.findById(idAssociate);
	}
}
