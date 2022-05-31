package br.com.southsystem.assembleia.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.southsystem.assembleia.entidade.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

	Optional<Associado> findByCpf(String cpf);

}
