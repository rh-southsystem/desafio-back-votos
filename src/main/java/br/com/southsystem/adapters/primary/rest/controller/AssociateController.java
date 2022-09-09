package br.com.southsystem.adapters.primary.rest.controller;

import br.com.southsystem.adapters.primary.rest.dto.AssociateResponse;
import br.com.southsystem.adapters.primary.rest.exception.ErrorResponse;
import br.com.southsystem.adapters.primary.rest.mapper.AssociateMapper;
import br.com.southsystem.application.port.primary.AssociatePrimaryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/associates")
public class AssociateController {

    private final AssociatePrimaryPort associatePrimaryPort;
    private final AssociateMapper mapper;

    @GetMapping("/v1.0/{cpfAssociate}")
    @Operation(
            summary = "Get a associate by cpf",
            responses = {
                @ApiResponse(responseCode = "200", description = "Associate successfully retrieved", content = @Content(schema = @Schema(implementation = AssociateResponse.class))),
                @ApiResponse(responseCode = "404", description = "Associate not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Mono<AssociateResponse> getFindByCpf(@PathVariable("cpfAssociate") String cpfAssociate) {
        log.info("Get a associate by cpf: {}", cpfAssociate);
        return associatePrimaryPort.findByCpf(cpfAssociate).map(mapper::toResponse);
    }

    @GetMapping("/v1.0/all")
    @Operation(
            summary = "Get a list all associate",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of associate successfully retrieved", content = @Content(schema = @Schema(implementation = AssociateResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Associate not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Flux<AssociateResponse> getAll() {
        log.info("Get a list all associate");
        return associatePrimaryPort.getAll().map(mapper::toResponse);
    }

}
