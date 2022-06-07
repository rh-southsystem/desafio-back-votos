package br.com.southsystem.assembleia.controlador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.southsystem.assembleia.dto.PautaBasicoDTO;
import br.com.southsystem.assembleia.dto.PautaCompletoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface PautaController {

	@Operation(description = "Método para criar uma pauta.")
	@ApiResponse(responseCode = "201", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<Void> criar(@Valid @RequestBody PautaBasicoDTO pautaBasicoDTO);

	@Operation(description = "Método para alterar uma pauta.")
	@ApiResponse(responseCode = "200", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "404", description = "Erro - Objeto não encontrado.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<Void> alterar(@PathVariable(value = "id") Long id, @Valid @RequestBody PautaBasicoDTO pautaBasicoDTO);

	@Operation(description = "Método para consultar pautas.")
	@ApiResponse(responseCode = "200", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "404", description = "Erro - Objeto não encontrado.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<List<PautaCompletoDTO>> consultar(@RequestParam Optional<String> id, @RequestParam Optional<String> descricao,
			@RequestParam Optional<LocalDateTime> dtInicio, @RequestParam Optional<LocalDateTime> dtFim, Optional<Boolean> excluida);

	@Operation(description = "Método para excluir uma pauta.")
	@ApiResponse(responseCode = "200", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "204", description = "Sucesso - Sem conteúdo.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "404", description = "Erro - Objeto não encontrado.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<Void> excluir(@PathVariable(value = "id") Long id);

}