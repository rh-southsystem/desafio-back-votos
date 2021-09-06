package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
