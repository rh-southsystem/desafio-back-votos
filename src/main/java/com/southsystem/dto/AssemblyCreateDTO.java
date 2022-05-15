package com.southsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssemblyCreateDTO {
	
	private String title;
	private String description;
	private Long duration;

}
