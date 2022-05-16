package com.southsystem.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
	
	private Long timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

}
