package br.com.southsystem.application.port.secondary;

import br.com.southsystem.application.domain.entity.VoteSession;
import reactor.core.publisher.Mono;

public interface VoteSessionSecondaryRepositoryPort {
    Mono<VoteSession> findById(Long voteSessionId);

    Mono<VoteSession> save(VoteSession voteSession);

    Mono<Boolean> existsByDescription(String voteSessionDescription);

    Mono<Boolean> existsById(Long voteSessionId);

    Mono<Boolean> verifyStatusOfVoteSession(Long voteSessionId);

}
