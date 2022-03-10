package com.southsystem.votos.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CpfInvalidException extends RuntimeException {

    public CpfInvalidException(String msg) {
        super(msg);
    }
}
