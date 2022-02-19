package com.southsystem.votos.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AgendaDTO", description = "Objeto referente ao dados da pauta")
public class AgendaDTO {

    @ApiModelProperty(notes = "Id da pauta", example = "1")
    private Long id;

    @ApiModelProperty(notes = "Descrição da pauta", example = "Pauta referente a prestacão de contas")
    private String description;

}
