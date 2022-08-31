package br.com.southsystem.adapters.secondary.postgresql.repository.data;

import br.com.southsystem.adapters.secondary.postgresql.entity.VoteSessionPost;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VoteSessionDataRepository extends ReactiveCrudRepository<VoteSessionPost, Long> {
    Mono<Boolean> existsByDescription(String voteSessionDescription);
    Mono<Boolean> existsByIdAndEnabledTrue(Long voteSessionId);
}
