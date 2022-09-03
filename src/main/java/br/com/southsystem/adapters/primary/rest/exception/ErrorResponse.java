package br.com.southsystem.adapters.primary.rest.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime date;
    private List<String> message;
}
