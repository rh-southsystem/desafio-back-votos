package com.southsystem.votos.controller.doc;

import com.southsystem.votos.dto.AgendaDTO;
import com.southsystem.votos.dto.VoteDTO;
import com.southsystem.votos.dto.VotingResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AgendaControllerDoc {

    @Operation(description = "Serviço responsável por criar uma pauta")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Pauta criada.") })
    ResponseEntity<Void> createAgenda(@RequestBody AgendaDTO agenda);

    @Operation(description = "Serviço responsável por abrir uma sessão de votação de uma pauta")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Sessão de votação aberta.") })
    ResponseEntity<Void> openVoting(@PathVariable Long idAgenda, @RequestParam(defaultValue = "1") Integer time);

    @Operation(description = "Serviço responsável por realizar o voto em uma pauta")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Voto registro."),
            @ApiResponse(responseCode = "200", description = "Voto não registro.")})
    ResponseEntity<Void> sendVote(@RequestBody VoteDTO voteDTO);

    @Operation(description = "Serviço responsável por obter o resultado da votação de uma pauta")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Resultado obtido.") })
    ResponseEntity<VotingResultDTO> getVotingResult(@PathVariable Long agendaId);
}
