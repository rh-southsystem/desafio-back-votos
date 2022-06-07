package br.com.southsystem.assembleia.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.southsystem.assembleia.dto.VotacaoDTO;
import br.com.southsystem.assembleia.dto.VotoDTO;
import br.com.southsystem.assembleia.entidade.enums.VotoEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface VotacaoController {

    @Operation(description = "Método para criar um voto.")
    @ApiResponse(responseCode = "201", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
    ResponseEntity<Void> votar(@Valid @RequestBody VotoDTO votoDTO);
    
    @Operation(description = "Método para consultar pautas.")
	@ApiResponse(responseCode = "200", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "404", description = "Erro - Objeto não encontrado.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<List<VotacaoDTO>> consultar(@RequestParam Long idPauta, @RequestParam Optional<Long> idAssociado,
			@RequestParam Optional<VotoEnum> voto);


    
}