package com.southsystem.service.exception;

public class AssemblyNotStartedException extends RuntimeException {
	
	private static final long serialVersionUID = 2883462674680793930L;
	
	private static final String NOT_STARTED_MESSAGE = "This assembly is not started.";

	public AssemblyNotStartedException() {
		super(NOT_STARTED_MESSAGE);
	}

}
