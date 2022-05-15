package com.southsystem.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.southsystem.service.exception.CannotUpdateAssemblyException;
import com.southsystem.service.exception.EntityNotFoundException;
import com.southsystem.service.exception.InvalidAssemblyToFinishVotingException;
import com.southsystem.service.exception.InvalidAssemblyToStartVotingException;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	private static final String NOT_FOUND = "Not Found";
	private static final String DATA_INTEGRITY = "Data Integrity";
	private static final String VALIDATION_ERROR = "Validation Error";
	
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
	
	@ExceptionHandler(InvalidAssemblyToStartVotingException.class)
	public ResponseEntity<StandardError> invalidAssemblyToStartVoting(InvalidAssemblyToStartVotingException e, HttpServletRequest req) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), DATA_INTEGRITY, e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(InvalidAssemblyToFinishVotingException.class)
	public ResponseEntity<StandardError> invalidAssemblyToFinishVoting(InvalidAssemblyToFinishVotingException e, HttpServletRequest req) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), DATA_INTEGRITY, e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest req) {
		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), VALIDATION_ERROR, e.getMessage(), req.getRequestURI());
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

}
