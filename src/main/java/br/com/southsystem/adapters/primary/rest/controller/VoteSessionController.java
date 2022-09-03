package br.com.southsystem.adapters.primary.rest.controller;

import br.com.southsystem.adapters.primary.rest.dto.AssociateResponse;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResponse;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResultResponse;
import br.com.southsystem.adapters.primary.rest.exception.ErrorResponse;
import br.com.southsystem.adapters.primary.rest.mapper.VoteSessionMapper;
import br.com.southsystem.application.port.primary.VoteSessionPrimaryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
@Log4j2
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
                @ApiResponse(responseCode = "404", description = "Vote Session not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    Mono<VoteSessionResponse> getFindById(@PathVariable("voteSessionId") Long voteSessionId) {
        log.info("Get a vote session by id: {}", voteSessionId);
        return voteSessionPrimaryPort.findById(voteSessionId).map(mapper::toDTO);
    }

    @GetMapping("/v1.0/all")
    @Operation(
            summary = "Get a list all vote session",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of vote session successfully retrieved", content = @Content(schema = @Schema(implementation = AssociateResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vote Session not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Flux<VoteSessionResponse> getAllVoteSession() {
        log.info("Get a list all vote session");
        return voteSessionPrimaryPort.getAll().map(mapper::toDTO);
    }

    @GetMapping("/v1.0/{voteSessionId}/finish")
    @Operation(
            summary = "finishing a vote session by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vote Session with result successfully retrieved", content = @Content(schema = @Schema(implementation = VoteSessionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vote Session not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    Mono<VoteSessionResultResponse> finishVoteSession(@PathVariable("voteSessionId") Long voteSessionId) {
        log.info("Finishing a vote session by id: {}", voteSessionId);
        return voteSessionPrimaryPort.finishVoteSession(voteSessionId).map(mapper::toResultDTO);
    }

    @GetMapping("/v1.0/{voteSessionId}/result")
    @Operation(
            summary = "Get a vote session by id with results",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vote Session with result successfully retrieved", content = @Content(schema = @Schema(implementation = VoteSessionResultResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vote Session not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    Mono<VoteSessionResultResponse> getFindByIdWithResult(@PathVariable("voteSessionId") Long voteSessionId) {
        log.info("Get a vote session with results by id : {}", voteSessionId);
        return voteSessionPrimaryPort.findVoteSessionByIdWithResult(voteSessionId).map(mapper::toResultDTO);
    }

    @PostMapping("/v1.0")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new vote session",
            responses = {
                @ApiResponse(responseCode = "201", description = "Vote Session created with success", content = @Content(schema = @Schema(implementation = VoteSessionResponse.class))),
                @ApiResponse(responseCode = "400", description = "A bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Mono<VoteSessionResponse> saveVoteSession(@RequestBody @Valid VoteSessionRequest requestDTO) {
        log.info("Create a new vote session request body : {}", requestDTO);
        return voteSessionPrimaryPort.saveVoteSession(mapper.toEntity(requestDTO)).map(mapper::toDTO);
    }
}
