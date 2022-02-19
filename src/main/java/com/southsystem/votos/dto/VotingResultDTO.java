package com.southsystem.votos.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "VotingResultDTO", description = "Objeto referente ao resultado da votação")
public class VotingResultDTO {

    @ApiModelProperty(notes = "Id da pauta", example = "1")
    private Long agendaId;

    @ApiModelProperty(notes = "Quantidade de votos 'SIM'", example = "5")
    private Long countVotesYes;

    @ApiModelProperty(notes = "Quantidade de votos 'NAO'", example = "3")
    private Long countVotesNo;

}
