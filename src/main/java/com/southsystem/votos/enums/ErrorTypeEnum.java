package com.southsystem.votos.enums;

public enum ErrorTypeEnum {

    BAD_REQUEST("Bad request"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    NOT_FOUND("Not found"),
    CONFLICT("Conflict"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    INVALID_TOKEN("Invalid token"),
    UNPROCESSABLE_ENTITY("Unprocessable Entity");

    private final String error;

    ErrorTypeEnum(String error) {
        this.error = error;
    }

    public String getMessage() {
        return this.error;
    }
}
