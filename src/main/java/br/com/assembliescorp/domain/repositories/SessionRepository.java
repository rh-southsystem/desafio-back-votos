package br.com.assembliescorp.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.assembliescorp.domain.entities.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

}
