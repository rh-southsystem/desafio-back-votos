package br.com.southsystem.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VotingNotFoundException extends IllegalArgumentException {
    public VotingNotFoundException() {
        super("Voting not found");
    }
}
