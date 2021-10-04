package br.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.entity.Associado;
import br.com.entity.Pauta;
import br.com.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	Optional<Voto> findByPautaAndAssociado(Pauta pauta, Associado associado);

	List<Voto> findByPauta(Pauta pauta);

}
