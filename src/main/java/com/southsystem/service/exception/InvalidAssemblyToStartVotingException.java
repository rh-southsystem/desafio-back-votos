package com.southsystem.service.exception;

public class InvalidAssemblyToStartVotingException extends RuntimeException {
	
	private static final long serialVersionUID = -1203406692548281118L;
	
	private static final String INVALID_ASSEMBLY_MESSAGE = "This assembly has already been voted or canceled.";

	public InvalidAssemblyToStartVotingException() {
		super(INVALID_ASSEMBLY_MESSAGE);
	}

}
