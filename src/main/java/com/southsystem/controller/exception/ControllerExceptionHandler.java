package com.southsystem.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.southsystem.service.exception.CannotUpdateAssemblyException;
import com.southsystem.service.exception.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	private static final String NOT_FOUND = "Not Found";
	private static final String DATA_INTEGRITY = "Data Integrity";
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFoundException(EntityNotFoundException e, HttpServletRequest req) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), NOT_FOUND, e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(CannotUpdateAssemblyException.class)
	public ResponseEntity<StandardError> dataIntegrity(CannotUpdateAssemblyException e, HttpServletRequest req) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), DATA_INTEGRITY, e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
