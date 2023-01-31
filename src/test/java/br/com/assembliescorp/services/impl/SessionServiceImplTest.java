package br.com.assembliescorp.services.impl;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.assembliescorp.domain.dtos.session.SessionCreateDTO;
import br.com.assembliescorp.domain.entities.SessionEntity;
import br.com.assembliescorp.domain.repositories.SessionRepository;
import br.com.assembliescorp.resources.exceptions.NotFoundEntityException;
import br.com.assembliescorp.resources.exceptions.SessionClosedException;

@ExtendWith(MockitoExtension.class)
public class SessionServiceImplTest {

	@Mock
	private SessionRepository sessionRepository;

	private SessionServiceImpl serviceImpl;

	@BeforeEach
	void setUp() {
		serviceImpl = new SessionServiceImpl(sessionRepository);
	}

	@Test
	void findSessionNotClosedWhenFinishIsNotNull() {
		when(sessionRepository.findById(any())).thenReturn(Optional.of(buildSessionEntity()));
		assertThrows(SessionClosedException.class, () -> serviceImpl.findSessionNotClosed(1L));
	}

	@Test
	void findSessionExpirated() {
		when(sessionRepository.findById(any())).thenReturn(Optional.of(buildSessionEntity()));		
		assertThrows(NotFoundEntityException.class, () -> serviceImpl.findSessionExpirated(1L));

	}
	
	@Test
	void getList() {
//		when(sessionRepository.findById(any())).thenReturn(Optional.of(buildSessionEntity()));
//		doNothing().when(sessionRepository).save(any(SessionEntity.class));
		assertThat(serviceImpl.getList()).isNotNull();

	}
	
	@Test
	void finishSession() {
		assertDoesNotThrow(() -> serviceImpl.finishSession(buildSessionEntity(),"Teste"));

	}

	private SessionCreateDTO buildSessionCreateDTO() {
		return SessionCreateDTO.builder().id(1L).name("Extraordinária").idRuling(1L).minutes(1L).build();
	}

	private SessionEntity buildSessionEntity() {
		return SessionEntity.builder().begin(LocalDateTime.now()).minutes(1L).begin(LocalDateTime.now()).finish(LocalDateTime.now()).build();
	}

}
