package br.com.southsystem.cooperative.exception;

public class SessionIsNotOpenException extends RuntimeException{
    public SessionIsNotOpenException(String message){
        super(message);
    }
}
