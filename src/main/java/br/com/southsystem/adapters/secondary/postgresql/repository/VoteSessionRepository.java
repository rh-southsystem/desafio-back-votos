package br.com.southsystem.adapters.secondary.postgresql.repository;

import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.VoteSessionDataRepository;
import br.com.southsystem.application.domain.entity.VoteSession;
import br.com.southsystem.application.port.secondary.VoteSessionSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VoteSessionRepository implements VoteSessionSecondaryRepositoryPort {

    private final VoteSessionDataRepository voteSessionDataRepository;
    private final PostgreSqlMapper sqlMapper;

    @Override
    public Mono<VoteSession> findById(Long voteSessionPostId) {
        return voteSessionDataRepository.findById(voteSessionPostId).map(sqlMapper::toVoteSessionEntity);
    }

    @Override
    public Mono<VoteSession> save(VoteSession voteSession) {
        return voteSessionDataRepository.save(sqlMapper.toVoteSessionPost(voteSession)).map(sqlMapper::toVoteSessionEntity);
    }

    @Override
    public Mono<Boolean> existsByDescription(String voteSessionPostDescription) {
        return voteSessionDataRepository.existsByDescription(voteSessionPostDescription);
    }

    @Override
    public Mono<Boolean> existsById(Long voteSessionId) {
        return voteSessionDataRepository.existsById(voteSessionId);
    }

    @Override
    public Mono<Boolean> verifyStatusOfVoteSession(Long voteSessionId) {
        return voteSessionDataRepository.existsByIdAndEnabledTrue(voteSessionId);
    }

}
