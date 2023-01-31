package br.com.assembliescorp.domain.dtos.associate;

import br.com.assembliescorp.domain.entities.AssociateEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AssociateCreateDTO(		
		Long id,
		
		@NotEmpty
		String name,
		
		@NotEmpty
		@Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}")
		String cpf
		) {
	
	public AssociateCreateDTO(AssociateEntity associateEntity) {
		this(associateEntity.getId(), associateEntity.getName(), associateEntity.getCpf());	
	}
	 
}
