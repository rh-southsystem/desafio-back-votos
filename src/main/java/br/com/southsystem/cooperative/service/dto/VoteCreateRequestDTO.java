package br.com.southsystem.cooperative.service.dto;

import br.com.southsystem.cooperative.domain.enumeration.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteCreateRequestDTO {

    private VoteType vote;

    private Long sessionId;

    private String affiliatedCpf;

}
