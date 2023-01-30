package br.com.assembliescorp.domain.dtos.associate;

import br.com.assembliescorp.domain.entities.AssociateEntity;

public record AssociateListDTO(
		Long id,
		String name,
		String cpf
		) {
	
	public AssociateListDTO(AssociateEntity associateEntity) {
		this(associateEntity.getId(), associateEntity.getName(), associateEntity.getCpf());
	}
}
