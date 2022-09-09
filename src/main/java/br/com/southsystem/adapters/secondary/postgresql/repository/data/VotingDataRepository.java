package br.com.southsystem.adapters.secondary.postgresql.repository.data;

import br.com.southsystem.adapters.secondary.postgresql.entity.VotingPost;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VotingDataRepository extends ReactiveCrudRepository<VotingPost, Long> {
    Mono<Boolean> existsByVoteSessionIdAndAssociateId(Long voteSessionId, Long associateId);

    Flux<VotingPost> findAllByVoteSessionId(Long voteSessionId);
}
