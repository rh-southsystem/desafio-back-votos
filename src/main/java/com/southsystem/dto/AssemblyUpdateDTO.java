package com.southsystem.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssemblyUpdateDTO {
	
	@NotNull(message="Id cannot be empty.")
	private Integer id;
	
	@NotEmpty(message="Title is mandatory.")
	@Size(min=3, max=50, message="Title size must be between 3 and 50 characters.")
	private String title;
	
	@NotEmpty(message="Description is mandatory.")
	@Size(min=5, max=255, message="Description size must be between 5 and 255 characters.")
	private String description;
	
	@NotEmpty(message="Duration is mandatory.")
	@Min(value = 60000, message = "The minimum accepted duration is 1 minute.")
	@Max(value = 43200000 , message = "The maximum accepted duration is 12 hours.")
	private Long duration;

}
