package br.com.southsystem.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssociateNotFoundException extends IllegalArgumentException {
    public AssociateNotFoundException(String cpfAssociado) {
        super("Could not find cpf associate "+ cpfAssociado);
    }
}
