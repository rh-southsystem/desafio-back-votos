package br.com.southsystem.cooperative.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionVotingResultDTO implements Serializable {

    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Long subjectId;

    private String subjectSubject;

    private long yesVotes;

    private long noVotes;

    public boolean isApproved() {
        return yesVotes > noVotes;
    }
}
