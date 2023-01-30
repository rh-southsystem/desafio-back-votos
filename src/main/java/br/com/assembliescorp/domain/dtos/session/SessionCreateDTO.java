package br.com.assembliescorp.domain.dtos.session;

import br.com.assembliescorp.domain.entities.SessionEntity;

public record SessionCreateDTO(		
		Long id,
		Long minutes
		) {
	
	public SessionCreateDTO(SessionEntity sessionEntity) {
		this(sessionEntity.getId(), sessionEntity.getMinutes());	
	}
	 
}
