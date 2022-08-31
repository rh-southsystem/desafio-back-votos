package br.com.southsystem.application.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class VoteSession {
    private Long id;
    private String description;
    private Boolean enabled;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ResultVoteSession result;
}
