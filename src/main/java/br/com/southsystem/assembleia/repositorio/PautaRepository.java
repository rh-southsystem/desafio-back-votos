package br.com.southsystem.assembleia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.southsystem.assembleia.entidade.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

	List<Pauta> findByFlFinalizadaFalse();

}
