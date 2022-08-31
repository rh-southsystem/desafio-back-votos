package br.com.southsystem.application.port.primary;

import br.com.southsystem.application.domain.entity.Voting;
import reactor.core.publisher.Mono;

public interface VotingPrimaryPort {
    Mono<Voting> findById(Long votingId);
    Mono<Voting> save(Voting voting);
}
