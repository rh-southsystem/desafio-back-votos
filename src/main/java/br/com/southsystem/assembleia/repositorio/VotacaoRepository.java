package br.com.southsystem.assembleia.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.southsystem.assembleia.entidade.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

	Optional<Votacao> findByVotoIdPautaId(Long idPauta);

	Optional<Votacao> findByVotoIdPautaIdAndVotoIdAssociadoId(Long idPauta, Long idAssociado);

}
