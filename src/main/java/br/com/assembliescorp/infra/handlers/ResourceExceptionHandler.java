package br.com.assembliescorp.infra.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.com.assembliescorp.resources.exceptions.NotFoundEntityException;
import br.com.assembliescorp.resources.exceptions.SessionClosedException;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(value = { NotFoundEntityException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundException(NotFoundEntityException ex, WebRequest request) {
		return buildErrorMessage(HttpStatus.NOT_FOUND.value(), "Objeto Não localizado",
				"Não foi possível localizar o objeto");
	}

	@ExceptionHandler(value = { SessionClosedException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundException(SessionClosedException ex, WebRequest request) {
		return buildErrorMessage(HttpStatus.BAD_REQUEST.value(), "Sessão já foi finalizada",
				"Não foi possível utilizar esta sessão pois ela já foi fechada");
	}

	private ErrorMessage buildErrorMessage(int code, String message, String description) {
		return ErrorMessage.builder().statusCode(code).timestamp(LocalDateTime.now()).message(message)
				.description(description).build();
	}

}
