package com.southsystem.votos.integration;

import com.southsystem.votos.dto.CPFIntegrationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpfIntegration", url = "${url-cpf-integration}")
public interface CPFIntegration {

    @GetMapping("/{cpf}")
    public ResponseEntity<CPFIntegrationDTO> isCPFValid(@PathVariable String cpf);

}
