package br.com.southsystem.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfInvalidException extends IllegalArgumentException {
    public CpfInvalidException() {
        super("CPF is invalid");
    }
}
