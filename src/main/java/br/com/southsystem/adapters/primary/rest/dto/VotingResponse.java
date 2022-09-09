package br.com.southsystem.adapters.primary.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VotingResponse {
    private Long id;
    private VoteSessionResponse voteSession;
    private AssociateResponse associate;
    private String voteType = "SECRET";
}
