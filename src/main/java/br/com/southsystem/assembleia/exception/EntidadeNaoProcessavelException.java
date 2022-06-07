package br.com.southsystem.assembleia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class EntidadeNaoProcessavelException extends RuntimeException {
	
	private static final long serialVersionUID = 7080933585789748479L;

	public EntidadeNaoProcessavelException() {
		super("Entidade não processável.");
	}

	public EntidadeNaoProcessavelException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntidadeNaoProcessavelException(String message) {
		super(message);
	}

	public EntidadeNaoProcessavelException(Throwable cause) {
		super(cause);
	}
}