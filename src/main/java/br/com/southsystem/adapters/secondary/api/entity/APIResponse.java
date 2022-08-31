package br.com.southsystem.adapters.secondary.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class APIResponse {
    public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";

    private String status;
}
