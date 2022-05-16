package com.southsystem.service.exception;

public class AssociateAlreadyVotedException extends RuntimeException {
	
	private static final long serialVersionUID = -3478009627666756418L;
	
	private static final String ALREADY_VOTED_MESSAGE = "This associate has voted already.";

	public AssociateAlreadyVotedException() {
		super(ALREADY_VOTED_MESSAGE);
	}

}
