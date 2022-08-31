package br.com.southsystem.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VoteSessionNotFoundException extends IllegalArgumentException {
    public VoteSessionNotFoundException() {
        super("Vote Session not found");
    }
}
