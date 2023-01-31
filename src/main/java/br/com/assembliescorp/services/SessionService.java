package br.com.assembliescorp.services;

import java.util.List;
import java.util.Optional;

import br.com.assembliescorp.domain.dtos.session.SessionCreateDTO;
import br.com.assembliescorp.domain.dtos.session.SessionListDTO;
import br.com.assembliescorp.domain.entities.SessionEntity;

public interface SessionService {
	
	List<SessionListDTO> getList();	
	SessionCreateDTO create(SessionCreateDTO sessionCreateDTO);
	SessionEntity findSessionNotClosedOrExpirated(Long idSession);
	Optional<SessionEntity> findById(Long idSession);
	void finishSession(Long idSession, String jsonResult);

}
