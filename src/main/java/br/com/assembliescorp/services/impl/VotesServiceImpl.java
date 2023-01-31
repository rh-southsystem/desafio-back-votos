package br.com.assembliescorp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.assembliescorp.domain.dtos.vote.VoteDTO;
import br.com.assembliescorp.domain.dtos.vote.VoteProcess;
import br.com.assembliescorp.domain.entities.AssociateEntity;
import br.com.assembliescorp.domain.entities.RulingEntity;
import br.com.assembliescorp.domain.entities.SessionEntity;
import br.com.assembliescorp.domain.entities.VoteEntity;
import br.com.assembliescorp.domain.projections.VoteGroupProjection;
import br.com.assembliescorp.domain.repositories.VoteRepository;
import br.com.assembliescorp.resources.exceptions.NotFoundEntityException;
import br.com.assembliescorp.services.AssociateService;
import br.com.assembliescorp.services.RulingService;
import br.com.assembliescorp.services.SendRabbitService;
import br.com.assembliescorp.services.SessionService;
import br.com.assembliescorp.services.VoteService;
import jakarta.transaction.Transactional;

@Service
public class VotesServiceImpl implements VoteService {

	private final VoteRepository voteRepository;
	private final AssociateService associateService;
	private final RulingService rulingService;
	private final SessionService sessionService;
	private final ObjectMapper objectMapper;
	private final SendRabbitService sendRabbitService;

	@Autowired
	public VotesServiceImpl(VoteRepository voteRepository, AssociateService associateService,
			RulingService rulingService, SessionService sessionService, ObjectMapper objectMapper,
			SendRabbitService sendRabbitService) {
		this.voteRepository = voteRepository;
		this.associateService = associateService;
		this.rulingService = rulingService;
		this.sessionService = sessionService;
		this.objectMapper = objectMapper;
		this.sendRabbitService = sendRabbitService;
	}

	public VoteDTO vote(VoteDTO voteDTO) {
		RulingEntity ruling = rulingService.findOne(voteDTO.idSession()).orElseThrow(NotFoundEntityException::new);
		SessionEntity session = sessionService.findSessionExpirated(voteDTO.idSession());
		AssociateEntity associate = associateService.findOne(voteDTO.idAssociate())
				.orElseThrow(NotFoundEntityException::new);
		var voteEntity = new VoteEntity(session, associate, Boolean.FALSE, voteDTO.value());
		voteRepository.save(voteEntity);
		return new VoteDTO(voteEntity);
	}

	@Transactional
	public void process(VoteProcess voteProcess) {

		Long idSession = voteProcess.idSession();
		SessionEntity session = sessionService.findSessionNotClosed(idSession);

		List<VoteGroupProjection> votes = voteRepository.getCountBySession(idSession);
		voteRepository.process(idSession);

		String votesJson = null;
		String sendRabbitMessage = null;
		Object[] objects = { session, votes };

		try {
			votesJson = objectMapper.writeValueAsString(votes);
			sendRabbitMessage = objectMapper.writeValueAsString(objects);
		} catch (JsonProcessingException e) {
			sendRabbitMessage = votesJson = "inconsistente";
		}

		sessionService.finishSession(session, votesJson);

		sendRabbitService.sendRabbit(sendRabbitMessage);

	}

}
