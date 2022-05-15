package com.southsystem.dto;

import com.southsystem.domain.enums.AssociatePermission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociateReadDTO {
	
	private Integer id;
	private String cpf;
	private String name;
	private String permission;
	
	public void setPermission(Integer permission) {
		this.permission = AssociatePermission.toEnum(permission).getDescription();
	}

}
