package br.com.assembliescorp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.dtos.VoteProcess;
import br.com.assembliescorp.domain.dtos.vote.VoteDTO;
import br.com.assembliescorp.domain.entities.AssociateEntity;
import br.com.assembliescorp.domain.entities.VoteEntity;
import br.com.assembliescorp.domain.projections.VoteGroupProjection;
import br.com.assembliescorp.domain.repositories.VoteRepository;
import br.com.assembliescorp.resources.exceptions.NotFoundEntity;
import br.com.assembliescorp.services.AssociateService;
import br.com.assembliescorp.services.RulingService;
import br.com.assembliescorp.services.SessionService;
import br.com.assembliescorp.services.VoteService;
import jakarta.transaction.Transactional;

@Service
public class VotesServiceImpl implements VoteService {
	
	private final VoteRepository voteRepository;
	private final AssociateService associateService;
	private final RulingService rulingService;
	private final SessionService sessionService;
	
	@Autowired
	public VotesServiceImpl(VoteRepository voteRepository, AssociateService associateService, RulingService rulingService, SessionService sessionService) {
		this.voteRepository = voteRepository;
		this.associateService = associateService;
		this.rulingService = rulingService;
		this.sessionService = sessionService;
	}

	public VoteDTO vote(VoteDTO voteDTO) {
//		var session = 
//		var ruling =
		AssociateEntity associate =  associateService.findOne(voteDTO.idAssociate()).orElseThrow(NotFoundEntity::new);	
		var voteEntity = new VoteEntity(null, null, associate, Boolean.FALSE, voteDTO.value());		
		voteRepository.save(voteEntity);
		return new VoteDTO(voteEntity);		
	}

	@Transactional
	public void process(VoteProcess voteProcess) {
		
		Long idSession = voteProcess.idSession();
		
		//verifica se a sessão existe
		//verifica se ela não está fechada
		List<VoteGroupProjection> votes = voteRepository.getCountBySession(idSession);
				
		//recupera os totais por sessão
		
		//grava no session e finaliza
		voteRepository.process(idSession);
		//coloca os votos como apurados		
		//TODO - verificar um modo melhor de gravar os totais
		String results = votes.stream().map(map -> map.getValue().concat("-").concat(map.getTotal())).toString();
		sessionService.finishSession(idSession, results);
	   
	}

}
	