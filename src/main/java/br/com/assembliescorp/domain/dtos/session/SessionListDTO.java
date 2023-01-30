package br.com.assembliescorp.domain.dtos.session;

import java.time.LocalDateTime;

import br.com.assembliescorp.domain.entities.SessionEntity;

public record SessionListDTO(
		Long id,
		Long minutes,
		LocalDateTime begin,
		LocalDateTime finish,
		String result
		) {
	
	public SessionListDTO(SessionEntity sessionEntity) {
		this(sessionEntity.getId(), sessionEntity.getMinutes(), sessionEntity.getBegin(), sessionEntity.getFinish(), sessionEntity.getResult());
	}
}
