package br.com.southsystem.application.domain.entity;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class Associate {
    private Long id;
    private String name;
    private String cpf;
}
