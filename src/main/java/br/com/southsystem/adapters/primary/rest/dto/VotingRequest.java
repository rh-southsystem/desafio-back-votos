package br.com.southsystem.adapters.primary.rest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VotingRequest {
    @NotNull(message = "The Vote Session cannot be null")
    private Long voteSessionId;

    @NotNull(message = "The Associate cannot be null")
    private Long associateId;

    @NotNull(message = "The Vote Type cannot be null")
    @Pattern(regexp = "YES|NO", message = "must be any of enum \"{regexp}\"")
    private String voteType;
}
