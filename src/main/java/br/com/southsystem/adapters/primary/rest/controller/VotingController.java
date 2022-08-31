package br.com.southsystem.adapters.primary.rest.controller;

import br.com.southsystem.adapters.primary.rest.dto.VotingRequest;
import br.com.southsystem.adapters.primary.rest.dto.VotingResponse;
import br.com.southsystem.adapters.primary.rest.mapper.VotingMapper;
import br.com.southsystem.application.port.primary.VotingPrimaryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/voting")
public class VotingController {

    private final VotingPrimaryPort votingPrimaryPort;
    private final VotingMapper mapper;

    @GetMapping("/v1.0/{votingId}")
    @Operation(
            summary = "Get a voting by id",
            responses = {
                @ApiResponse(responseCode = "200", description = "Voting successfully retrieved", content = @Content(schema = @Schema(implementation = VotingResponse.class))),
                @ApiResponse(responseCode = "404", description = "Voting not found")
            })
    public Mono<VotingResponse> getFindById(@PathVariable("votingId") Long votingId) {
        return votingPrimaryPort.findById(votingId).map(mapper::toResponse);
    }

    @PostMapping("/v1.0")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Save a new vote session",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Voting saving with success", content = @Content(schema = @Schema(implementation = VotingResponse.class))),
                    @ApiResponse(responseCode = "400", description = "A bad request")
            })
    public Mono<VotingResponse> saveVoting(@RequestBody @Valid VotingRequest requestDTO) {
        return votingPrimaryPort.save(mapper.toEntity(requestDTO)).map(mapper::toResponse);
    }
}
