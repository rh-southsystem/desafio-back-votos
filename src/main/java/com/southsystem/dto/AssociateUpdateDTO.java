package com.southsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociateUpdateDTO {
	
	private Integer id;
	private String cpf;
	private String name;

}
