package br.com.southsystem.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ResultVoteSession {
    private Long scoreAgainst;
    private Long scoreFor;
    private Long scoreTotal;
}
