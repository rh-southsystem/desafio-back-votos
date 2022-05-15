package com.southsystem.service.exception;

public class InvalidAssemblyToFinishVotingException  extends RuntimeException {
	
	private static final long serialVersionUID = -2246549479984635755L;
	
	private static final String INVALID_ASSEMBLY_MESSAGE = "This assembly has not been started.";

	public InvalidAssemblyToFinishVotingException() {
		super(INVALID_ASSEMBLY_MESSAGE);
	}

}
