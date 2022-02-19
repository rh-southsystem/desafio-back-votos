package com.southsystem.votos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteTypeEnum {

    SIM("Sim"),
    NAO("Não");

    private String value;

}
