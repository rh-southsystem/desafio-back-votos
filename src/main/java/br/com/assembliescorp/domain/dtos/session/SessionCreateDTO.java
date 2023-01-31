package br.com.assembliescorp.domain.dtos.session;

import br.com.assembliescorp.domain.entities.SessionEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SessionCreateDTO(		
		Long id,
		@NotEmpty
		String name,
		@NotEmpty
		Long idRuling,
		Long minutes
		) {
	
	public SessionCreateDTO(SessionEntity sessionEntity) {
		this(sessionEntity.getId(), sessionEntity.getName(), sessionEntity.getRuling().getId(), sessionEntity.getMinutes());	
	}
	 
}
