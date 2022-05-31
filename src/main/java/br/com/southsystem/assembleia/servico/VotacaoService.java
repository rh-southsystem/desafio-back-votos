package br.com.southsystem.assembleia.servico;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.southsystem.assembleia.entidade.Votacao;
import br.com.southsystem.assembleia.entidade.enums.VotoEnum;

public interface VotacaoService {

	Optional<Votacao> votar(@Valid Votacao votacao);

	Optional<List<Votacao>> consultar(@Valid @NotNull(message = "O campo idPauta é obrigatório.") Long idPauta, Optional<Long> idAssociado, Optional<VotoEnum> voto);

}
