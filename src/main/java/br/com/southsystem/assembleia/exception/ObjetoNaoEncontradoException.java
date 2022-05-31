package br.com.southsystem.assembleia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjetoNaoEncontradoException extends RuntimeException {
	
	private static final long serialVersionUID = 7080933585789748479L;

	public ObjetoNaoEncontradoException() {
		super("Objeto informado não foi encontrado.");
	}

	public ObjetoNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjetoNaoEncontradoException(String message) {
		super(message);
	}

	public ObjetoNaoEncontradoException(Throwable cause) {
		super(cause);
	}
}