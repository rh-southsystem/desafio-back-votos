package br.com.assembliescorp.domain.dtos.session;

import br.com.assembliescorp.domain.entities.SessionEntity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SessionCreateDTO(		
		@Schema(hidden = true)
		Long id,
		@NotEmpty
		String name,
		@NotEmpty
		Long idRuling,
		@Min(value = 1)
		Long minutes
		) {
	
	public SessionCreateDTO(SessionEntity sessionEntity) {
		this(sessionEntity.getId(), sessionEntity.getName(), sessionEntity.getRuling().getId(), sessionEntity.getMinutes());	
	}
	 
}
