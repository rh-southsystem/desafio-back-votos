package com.southsystem.service.exception;

public class EntityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -2246549479984635755L;
	
	private static final String NOT_FOUND_MESSAGE = "The requested entity was not found.";

	public EntityNotFoundException() {
		super(NOT_FOUND_MESSAGE);
	}

}
