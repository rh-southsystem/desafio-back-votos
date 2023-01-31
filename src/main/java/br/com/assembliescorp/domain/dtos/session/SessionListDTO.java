package br.com.assembliescorp.domain.dtos.session;

import java.time.LocalDateTime;

import br.com.assembliescorp.domain.entities.SessionEntity;

public record SessionListDTO(
		Long id,
		String name,
		Long idRuling,
		Long minutes,
		LocalDateTime begin,
		LocalDateTime finish,
		String result
		) {
	
	public SessionListDTO(SessionEntity sessionEntity) {
		this(sessionEntity.getId(), sessionEntity.getName(), sessionEntity.getRuling().getId(), sessionEntity.getMinutes(), sessionEntity.getBegin(), sessionEntity.getFinish(), sessionEntity.getResult());
	}
}
