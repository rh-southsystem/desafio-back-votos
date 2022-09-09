package br.com.southsystem.application.port.primary;

import br.com.southsystem.application.domain.entity.VoteSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VoteSessionPrimaryPort {
    Mono<VoteSession> findById(Long voteSessionId);
    Mono<VoteSession> saveVoteSession(VoteSession voteSession);

    Mono<VoteSession> findVoteSessionByIdWithResult(Long voteSessionId);

    Mono<VoteSession> finishVoteSession(Long voteSessionId);

    Flux<VoteSession> getAll();
}
