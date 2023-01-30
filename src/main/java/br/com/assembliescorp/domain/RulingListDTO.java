package br.com.assembliescorp.domain;

import br.com.assembliescorp.domain.entities.RulingEntity;

public record RulingListDTO(
		Long id,
		String name
		) {
	
	public RulingListDTO(RulingEntity rulingEntity) {
		this(rulingEntity.getId(), rulingEntity.getName());	
	}
}
