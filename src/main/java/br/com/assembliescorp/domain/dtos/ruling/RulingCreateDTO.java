package br.com.assembliescorp.domain.dtos.ruling;

import br.com.assembliescorp.domain.entities.RulingEntity;
import jakarta.validation.constraints.NotEmpty;

public record RulingCreateDTO(		
		Long id,
		@NotEmpty
		String name
		) {
	
	public RulingCreateDTO(RulingEntity rulingEntity) {
		this(rulingEntity.getId(), rulingEntity.getName());	
	}
	 
}
