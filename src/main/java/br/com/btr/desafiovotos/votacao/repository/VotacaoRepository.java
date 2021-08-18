package br.com.btr.desafiovotos.votacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.btr.desafiovotos.votacao.entity.VotacaoEntity;

public interface VotacaoRepository extends CrudRepository<VotacaoEntity, Long>{

	List<VotacaoEntity> findAllByPautaId(Long idPauta);

	Optional<VotacaoEntity> findByPautaIdAndAssociadoId(Long idPauta, Long idAssociado);
}
