package br.com.southsystem.application.domain.entity;

import br.com.southsystem.application.domain.entity.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Voting {
    private Long id;
    private VoteSession voteSession;
    private Associate associate;
    private VoteType voteType;
}
