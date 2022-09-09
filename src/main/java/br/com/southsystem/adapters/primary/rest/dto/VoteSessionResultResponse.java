package br.com.southsystem.adapters.primary.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VoteSessionResultResponse {
    private Long id;
    private String description;
    private ResultResponse result;

    @Value
    @Builder
    public static class ResultResponse {
        private Long scoreAgainst;
        private Long scoreFor;
        private Long scoreTotal;
    }
}

