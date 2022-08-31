package br.com.southsystem.application.port.secondary;

import br.com.southsystem.application.domain.entity.Voting;
import reactor.core.publisher.Flux;

public interface ResultVoteSessionSecondaryRepositoryPort {
    Flux<Voting> findAllByVoteSessionId(Long voteSessionId);
}
