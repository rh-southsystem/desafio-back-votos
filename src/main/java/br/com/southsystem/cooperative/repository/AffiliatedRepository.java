package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.domain.Affiliated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffiliatedRepository extends JpaRepository<Affiliated, Long> {
    Optional<Affiliated> findFirstByCpf(String cpf);
}
