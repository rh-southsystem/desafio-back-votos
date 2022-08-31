package br.com.southsystem.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VoteSessionExistsException extends IllegalArgumentException {
    public static final String MSG_VOTE_SESSION_DESC = "Description of vote session is already registered";
    public VoteSessionExistsException() {
        super(MSG_VOTE_SESSION_DESC);
    }
}
