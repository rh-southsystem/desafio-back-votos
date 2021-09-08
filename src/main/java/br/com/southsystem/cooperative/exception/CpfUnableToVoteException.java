package br.com.southsystem.cooperative.exception;

public class CpfUnableToVoteException extends RuntimeException {
    public CpfUnableToVoteException(String message) {
        super(message);
    }
}
