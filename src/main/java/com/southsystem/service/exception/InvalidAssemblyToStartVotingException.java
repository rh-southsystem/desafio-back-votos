package com.southsystem.service.exception;

public class InvalidAssemblyToStartVotingException  extends RuntimeException {
	
	private static final long serialVersionUID = -2246549479984635755L;
	
	private static final String INVALID_ASSEMBLY_MESSAGE = "This assembly has already been voted or canceled.";

	public InvalidAssemblyToStartVotingException() {
		super(INVALID_ASSEMBLY_MESSAGE);
	}

}
