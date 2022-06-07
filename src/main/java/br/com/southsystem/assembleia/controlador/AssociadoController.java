package br.com.southsystem.assembleia.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.southsystem.assembleia.dto.AssociadoBasicoDTO;
import br.com.southsystem.assembleia.dto.AssociadoCompletoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface AssociadoController {

	@Operation(description = "Método para criar um associado.")
	@ApiResponse(responseCode = "201", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<Void> criar(@Valid @RequestBody AssociadoBasicoDTO associadoBasicoDTO);

	@Operation(description = "Método para alterar um associado.")
	@ApiResponse(responseCode = "200", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "404", description = "Erro - Objeto não encontrado.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<Void> alterar(@PathVariable(value = "id") Long id, @Valid @RequestBody AssociadoBasicoDTO associadoBasicoDTO);

	@Operation(description = "Método para consultar associados.")
	@ApiResponse(responseCode = "200", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "404", description = "Erro - Objeto não encontrado.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<List<AssociadoCompletoDTO>> consultar(@RequestParam Optional<String> id, @RequestParam Optional<String> nome, Optional<Boolean> excluido);

	@Operation(description = "Método para excluir um associado.")
	@ApiResponse(responseCode = "200", description = "Sucesso - Pedido atendido com sucesso.")
	@ApiResponse(responseCode = "204", description = "Sucesso - Sem conteúdo.")
	@ApiResponse(responseCode = "400", description = "Erro - Solicitação inválida.", content = @Content)
	@ApiResponse(responseCode = "403", description = "Erro - Você não tem permissão para acessar este recurso.", content = @Content)
	@ApiResponse(responseCode = "404", description = "Erro - Objeto não encontrado.", content = @Content)
	@ApiResponse(responseCode = "422", description = "Erro - Entidade não processável.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Erro - Foi gerada uma exceção.", content = @Content)
	ResponseEntity<Void> excluir(@PathVariable(value = "id") Long id);

}