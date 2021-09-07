package br.com.southsystem.cooperative.exception;

public class BadRequestAlertException extends RuntimeException{
    public BadRequestAlertException(String message) {
        super(message);
    }
}
