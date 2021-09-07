package br.com.southsystem.cooperative.service.dto;

import br.com.southsystem.cooperative.domain.enumeration.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDTO {

    private Long id;

    private LocalDateTime voteDateTime;

    private VoteType vote;

    private Long sessionId;

    private LocalDateTime sessionStartDateTime;

    private LocalDateTime sessionEndDateTime;

    private Long affiliatedId;

    private String affiliatedCpf;



}
