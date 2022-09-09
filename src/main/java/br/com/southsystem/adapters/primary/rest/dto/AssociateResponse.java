package br.com.southsystem.adapters.primary.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AssociateResponse {
    private Long id;
    private String name;
    private String cpf;
}
