package com.southsystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociateCreateDTO {
	
	@NotEmpty(message="CPF is mandatory")
	@Size(min=11, max=11, message="CPF must be of 11 characters")
	private String cpf;
	
	@NotEmpty(message="Name is mandatory")
	@Size(min=3, max=50, message="Name size must be between 3 and 50 characters")
	private String name;

}
