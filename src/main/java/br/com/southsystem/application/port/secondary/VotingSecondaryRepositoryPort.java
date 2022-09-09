package br.com.southsystem.application.port.secondary;

import br.com.southsystem.application.domain.entity.Voting;
import reactor.core.publisher.Mono;

public interface VotingSecondaryRepositoryPort {
    Mono<Voting> findById(Long votingId);

    Mono<Boolean> existsByVoteSessionIdAndAssociateId(Long voteSessionId, Long associateId);

    Mono<Voting> save(Voting voting);
}
