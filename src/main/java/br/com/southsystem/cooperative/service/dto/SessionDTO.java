package br.com.southsystem.cooperative.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDTO {

    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Long subjectId;

    private String subjectSubject;

    private boolean open;

}
