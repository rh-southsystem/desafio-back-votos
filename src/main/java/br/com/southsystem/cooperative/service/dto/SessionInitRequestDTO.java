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
public class SessionInitRequestDTO {

    private LocalDateTime endDateTime;

    private Long subjectId;
}

