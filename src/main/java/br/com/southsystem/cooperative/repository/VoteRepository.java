package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findFirstByAffiliatedCpfAndSessionId(String cpf, Long sessionId);
}
