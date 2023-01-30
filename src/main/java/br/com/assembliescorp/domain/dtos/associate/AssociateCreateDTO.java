package br.com.assembliescorp.domain.dtos.associate;

import br.com.assembliescorp.domain.entities.AssociateEntity;

public record AssociateCreateDTO(		
		Long id,
		String name,
		String cpf
		) {
	
	public AssociateCreateDTO(AssociateEntity associateEntity) {
		this(associateEntity.getId(), associateEntity.getName(), associateEntity.getCpf());	
	}
	 
}
