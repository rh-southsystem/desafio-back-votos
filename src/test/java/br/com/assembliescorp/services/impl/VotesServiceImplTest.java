package br.com.assembliescorp.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.assembliescorp.domain.dtos.vote.VoteDTO;
import br.com.assembliescorp.domain.dtos.vote.VoteProcess;
import br.com.assembliescorp.domain.entities.AssociateEntity;
import br.com.assembliescorp.domain.entities.RulingEntity;
import br.com.assembliescorp.domain.entities.SessionEntity;
import br.com.assembliescorp.domain.repositories.VoteRepository;
import br.com.assembliescorp.services.AssociateService;
import br.com.assembliescorp.services.RulingService;
import br.com.assembliescorp.services.SendRabbitService;
import br.com.assembliescorp.services.SessionService;

@ExtendWith(MockitoExtension.class)
public class VotesServiceImplTest {
	
	VotesServiceImpl votesServiceImpl;
	
	@Mock
	VoteRepository voteRepository;
	@Mock
	AssociateService associateService;
	@Mock
	RulingService rulingService;
	@Mock
	SessionService sessionService;
	@Mock
	ObjectMapper objectMapper;
	@Mock
	SendRabbitService sendRabbitService;

	@BeforeEach
	void setUp() {
		votesServiceImpl = new VotesServiceImpl(voteRepository, associateService, rulingService, sessionService, objectMapper, sendRabbitService);
	}
	
	@Test
	void vote() {
		when(rulingService.findOne(any())).thenReturn(Optional.of(buildRulingEntity()));
		when(sessionService.findSessionExpirated(any())).thenReturn(buildSessionEntity());
		when(associateService.findOne(any())).thenReturn(Optional.of(buildAssociateEntity()));
		assertThat(votesServiceImpl.vote(buildVote())).isNotNull();
	}
	
	@Test
	void processWhenEnterIsnull() {
		assertThrows(NullPointerException.class, () -> votesServiceImpl.process(null));
	}
	
	@Test
	void processWhenVote() {
		assertDoesNotThrow(() -> votesServiceImpl.process(buildVoteProcess()));
	}	
	
	VoteDTO buildVote() {
		return VoteDTO.builder().build();
	}
	
	RulingEntity buildRulingEntity() {
		return RulingEntity.builder().build();
	}
	
	SessionEntity buildSessionEntity() {
		return SessionEntity.builder().build();
	}
	
	AssociateEntity buildAssociateEntity() {
		return AssociateEntity.builder().build();
	}
	
	VoteProcess buildVoteProcess() {
		return VoteProcess.builder().build();
	}
}
