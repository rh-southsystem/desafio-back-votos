package br.com.assembliescorp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.assembliescorp.domain.dtos.vote.VoteDTO;
import br.com.assembliescorp.domain.dtos.vote.VoteProcess;
import br.com.assembliescorp.domain.entities.AssociateEntity;
import br.com.assembliescorp.domain.entities.RulingEntity;
import br.com.assembliescorp.domain.entities.SessionEntity;
import br.com.assembliescorp.domain.entities.VoteEntity;
import br.com.assembliescorp.domain.projections.VoteGroupProjection;
import br.com.assembliescorp.domain.repositories.VoteRepository;
import br.com.assembliescorp.resources.exceptions.NotFoundEntityException;
import br.com.assembliescorp.resources.exceptions.SessionClosedException;
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
		RulingEntity ruling = rulingService.findOne(voteDTO.idSession()).orElseThrow(NotFoundEntityException::new);
		SessionEntity session = sessionService.findById(voteDTO.idSession()).orElseThrow(NotFoundEntityException::new);
		if(session.getFinish() != null) {
			throw new SessionClosedException();
		}
		AssociateEntity associate =  associateService.findOne(voteDTO.idAssociate()).orElseThrow(NotFoundEntityException::new);	
		var voteEntity = new VoteEntity(ruling, session, associate, Boolean.FALSE, voteDTO.value());		
		voteRepository.save(voteEntity);
		return new VoteDTO(voteEntity);		
	}

	@Transactional
	public void process(@RequestBody VoteProcess voteProcess) {
		
		Long idSession = voteProcess.idSession();
		
		SessionEntity session = sessionService.findById(idSession).orElseThrow(NotFoundEntityException::new);
		if(session.getFinish() != null) {
			throw new SessionClosedException();
		}		
				
		List<VoteGroupProjection> votes = voteRepository.getCountBySession(idSession);		
		voteRepository.process(idSession);		
		
		//TODO - verificar um modo melhor de gravar os totais
		String results = votes.stream().map(map -> map.getValue().concat("-").concat(map.getTotal())).toString();
		sessionService.finishSession(idSession, results);
	   
	}

}
	