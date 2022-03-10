package com.southsystem.votos.dto;

import com.southsystem.votos.enums.VoteTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "VoteDTO", description = "Objeto referente ao dados do voto")
public class VoteDTO {

    @ApiModelProperty(notes = "CPF do associado", example = "16767347248")
    private String cpf;

    @ApiModelProperty(notes = "Id da pauta", example = "1")
    private Long agendaId;

    @ApiModelProperty(notes = "Valor do voto", example = "SIM")
    private VoteTypeEnum voteType;
}
