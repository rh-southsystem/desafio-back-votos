package br.com.assembliescorp.domain.dtos.ruling;

import br.com.assembliescorp.domain.entities.RulingEntity;

public record RulingCreateDTO(		
		Long id,
		String name
		) {
	
	public RulingCreateDTO(RulingEntity rulingEntity) {
		this(rulingEntity.getId(), rulingEntity.getName());	
	}
	 
}
