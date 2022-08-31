package br.com.southsystem.adapters.primary.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class VoteSessionRequest {
    @NotBlank(message = "The description cannot be null")
    private String description;
    @NotNull(message = "The startDateTime cannot be null")
    private LocalDateTime startDateTime;
    @NotNull(message = "The endDateTime cannot be null")
    private LocalDateTime endDateTime;
}
