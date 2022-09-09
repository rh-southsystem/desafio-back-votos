package br.com.southsystem.adapters.primary.rest.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class VoteSessionResponse {
    private Long id;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
