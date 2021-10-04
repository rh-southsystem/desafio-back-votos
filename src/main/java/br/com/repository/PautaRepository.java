package br.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
