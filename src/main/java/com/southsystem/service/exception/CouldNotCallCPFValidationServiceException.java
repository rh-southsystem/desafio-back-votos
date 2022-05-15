package com.southsystem.service.exception;

public class CouldNotCallCPFValidationServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 5847072431903369651L;
	
	private static final String CPF_VALIDATION_MESSAGE = "An error occurred while trying to validate the CPF. Please, try again";

	public CouldNotCallCPFValidationServiceException() {
		super(CPF_VALIDATION_MESSAGE);
	}

}
