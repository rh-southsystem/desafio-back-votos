package com.southsystem.votos.dto;

import com.southsystem.votos.enums.CPFIntegrationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPFIntegrationDTO {

    private CPFIntegrationStatusEnum status;
}
