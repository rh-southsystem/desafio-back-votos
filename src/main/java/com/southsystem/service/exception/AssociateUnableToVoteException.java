package com.southsystem.service.exception;

public class AssociateUnableToVoteException extends RuntimeException {
	
	private static final long serialVersionUID = 2883462674680793930L;
	
	private static final String UNABLE_TO_VOTE_MESSAGE = "This associate is unable to vote, for his CPF is invalid";

	public AssociateUnableToVoteException() {
		super(UNABLE_TO_VOTE_MESSAGE);
	}

}
