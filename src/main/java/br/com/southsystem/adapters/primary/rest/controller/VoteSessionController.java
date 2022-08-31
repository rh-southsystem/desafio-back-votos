package br.com.southsystem.adapters.primary.rest.controller;

import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResponse;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResultResponse;
import br.com.southsystem.adapters.primary.rest.mapper.VoteSessionMapper;
import br.com.southsystem.application.port.primary.VoteSessionPrimaryPort;
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
@RequestMapping(value = "/votesessions")
public class VoteSessionController {

    private final VoteSessionPrimaryPort voteSessionPrimaryPort;
    private final VoteSessionMapper mapper;

    @GetMapping("/v1.0/{voteSessionId}")
    @Operation(
            summary = "Get a vote session by id",
            responses = {
                @ApiResponse(responseCode = "200", description = "Vote Session successfully retrieved", content = @Content(schema = @Schema(implementation = VoteSessionResponse.class))),
                @ApiResponse(responseCode = "404", description = "Vote Session not found")
            })
    Mono<VoteSessionResponse> getFindById(@PathVariable("voteSessionId") Long voteSessionId) {
        return voteSessionPrimaryPort.findById(voteSessionId).map(mapper::toDTO);
    }

    @GetMapping("/v1.0/{voteSessionId}/result")
    @Operation(
            summary = "Get a vote session by id with results",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vote Session successfully retrieved", content = @Content(schema = @Schema(implementation = VoteSessionResultResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vote Session not found")
            })
    Mono<VoteSessionResultResponse> getFindByIdWithResult(@PathVariable("voteSessionId") Long voteSessionId) {
        return voteSessionPrimaryPort.findVoteSessionByIdWithResult(voteSessionId).map(mapper::toResultDTO);
    }

    @PostMapping("/v1.0")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new vote session",
            responses = {
                @ApiResponse(responseCode = "201", description = "Vote Session created with success", content = @Content(schema = @Schema(implementation = VoteSessionResponse.class))),
                @ApiResponse(responseCode = "400", description = "A bad request")
            })
    public Mono<VoteSessionResponse> saveVoteSession(@RequestBody @Valid VoteSessionRequest requestDTO) {
        return voteSessionPrimaryPort.saveVoteSession(mapper.toEntity(requestDTO)).map(mapper::toDTO);
    }
}
