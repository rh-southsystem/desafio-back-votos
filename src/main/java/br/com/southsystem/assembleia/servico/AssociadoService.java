package br.com.southsystem.assembleia.servico;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import br.com.southsystem.assembleia.entidade.Associado;

@Validated
public interface AssociadoService {

	Optional<Associado> criar(@Valid Associado associado);

	Optional<Associado> alterar(Long id, @Valid Associado associado);

	Optional<List<Associado>> consultar(Optional<String> id, Optional<String> nome, Optional<Boolean> excluido);

	Optional<Associado> consultarPorId(Long id);

	void excluir(Long id);

}
