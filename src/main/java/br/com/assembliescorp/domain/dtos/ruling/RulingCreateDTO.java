package br.com.assembliescorp.domain.dtos.ruling;

import br.com.assembliescorp.domain.entities.RulingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RulingCreateDTO(
		@Schema(hidden = true)
		Long id,
		@NotEmpty
		String name
		) {
	
	public RulingCreateDTO(RulingEntity rulingEntity) {
		this(rulingEntity.getId(), rulingEntity.getName());	
	}
	 
}
