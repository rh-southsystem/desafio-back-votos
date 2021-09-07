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
public class SubjectDTO {

    private Long id;

    private String subject;

    private LocalDateTime dateTime;

    private Long sessionId;

    private LocalDateTime sessionStartDateTime;

    private LocalDateTime sessionEndDateTime;

}
