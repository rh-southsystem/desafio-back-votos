package com.southsystem.dto;

import com.southsystem.domain.enums.AssemblyStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssemblyReadDTO {
	
	private Integer id;
	private String title;
	private String description;
	private String status;
	private Long duration;
	
	public void setStatus(Integer status) {
		this.status = AssemblyStatus.toEnum(status).getDescription();
	}

}
