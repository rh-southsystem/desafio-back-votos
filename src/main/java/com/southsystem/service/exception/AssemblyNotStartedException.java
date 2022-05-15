package com.southsystem.service.exception;

public class AssemblyNotStartedException extends RuntimeException {
	
	private static final long serialVersionUID = 2883462674680793930L;
	
	private static final String NOT_STARTED_MESSAGE = "This assembly has not been started.";

	public AssemblyNotStartedException() {
		super(NOT_STARTED_MESSAGE);
	}

}
