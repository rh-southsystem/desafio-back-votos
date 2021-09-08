package br.com.southsystem.cooperative.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AffiliatedDTO {
    private Long id;
    private String nome;
    private String cpf;
}
