package br.com.assembliescorp.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.dtos.session.SessionCreateDTO;
import br.com.assembliescorp.domain.dtos.session.SessionListDTO;
import br.com.assembliescorp.domain.entities.SessionEntity;
import br.com.assembliescorp.domain.repositories.SessionRepository;
import br.com.assembliescorp.resources.exceptions.NotFoundEntityException;
import br.com.assembliescorp.resources.exceptions.SessionClosedException;
import br.com.assembliescorp.services.SessionService;

@Service
public class SessionServiceImpl implements SessionService {
	
	public final SessionRepository sessionRepository;
	
	public SessionServiceImpl(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	public List<SessionListDTO> getList() {
		return sessionRepository.findAll().stream().map(SessionListDTO::new).toList();
	}

	public SessionCreateDTO create(SessionCreateDTO sessionCreateDTO) {
		var session = new SessionEntity(sessionCreateDTO);
		sessionRepository.save(session);
		return new SessionCreateDTO(session);
	}

	public void finishSession(Long idSession, String jsonResult) {
		SessionEntity session = sessionRepository.findById(idSession).orElseThrow(NotFoundEntityException::new);
		if(session.getFinish() != null) {
			throw new SessionClosedException();
		}
		
		session.setFinish(LocalDateTime.now());
		session.setResult(jsonResult);
		sessionRepository.save(session);
	}

	@Override
	public Optional<SessionEntity> findById(Long idSession) {
		return sessionRepository.findById(idSession);
	}


}
	