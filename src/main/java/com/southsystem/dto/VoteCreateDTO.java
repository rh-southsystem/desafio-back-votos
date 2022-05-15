package com.southsystem.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteCreateDTO {
	
	@NotNull(message="Vote cannot be empty.")
	@Min(value = 1, message = "Vote must be YES or NO")
	@Max(value = 2 , message = "Vote must be YES or NO")
	private Integer vote;
	
	@NotNull(message="Assembly cannot be empty.")
	private Integer assembly;
	
	@NotNull(message="Associate cannot be empty.")
	private Integer associate;

}
