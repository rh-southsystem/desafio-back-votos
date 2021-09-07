package br.com.southsystem.cooperative.exception;

public class CpfNotFoundException extends RuntimeException {
    public CpfNotFoundException(String message) {
        super(message);
    }
}
