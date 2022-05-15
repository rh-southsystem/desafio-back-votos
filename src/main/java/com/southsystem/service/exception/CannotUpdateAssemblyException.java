package com.southsystem.service.exception;

public class CannotUpdateAssemblyException extends RuntimeException {
	
	private static final long serialVersionUID = -2246549479984635755L;
	
	private static final String CANNOT_UPDATE_MESSAGE = "This assembly is not on Pending status. Cannot be updated.";

	public CannotUpdateAssemblyException() {
		super(CANNOT_UPDATE_MESSAGE);
	}

}
