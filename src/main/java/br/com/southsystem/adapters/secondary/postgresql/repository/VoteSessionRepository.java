package br.com.southsystem.adapters.secondary.postgresql.repository;

import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.VoteSessionDataRepository;
import br.com.southsystem.application.domain.entity.VoteSession;
import br.com.southsystem.application.port.secondary.VoteSessionSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class VoteSessionRepository implements VoteSessionSecondaryRepositoryPort {

    private final VoteSessionDataRepository voteSessionDataRepository;
    private final PostgreSqlMapper sqlMapper;

    @Override
    public Mono<VoteSession> findById(Long voteSessionPostId) {
        log.debug("call method findById by id: {} ", voteSessionPostId);
        return voteSessionDataRepository.findById(voteSessionPostId).map(sqlMapper::toVoteSessionEntity);
    }

    @Override
    public Mono<VoteSession> save(VoteSession voteSession) {
        log.debug("call method save of objet: {} ", voteSession);
        return voteSessionDataRepository.save(sqlMapper.toVoteSessionPost(voteSession)).map(sqlMapper::toVoteSessionEntity);
    }

    @Override
    public Mono<Boolean> existsByDescription(String voteSessionPostDescription) {
        log.debug("call method existsByDescription by: {} ", voteSessionPostDescription);
        return voteSessionDataRepository.existsByDescription(voteSessionPostDescription);
    }

    @Override
    public Mono<Boolean> existsById(Long voteSessionId) {
        log.debug("call method existsById by Id: {} ", voteSessionId);
        return voteSessionDataRepository.existsById(voteSessionId);
    }

    @Override
    public Mono<Boolean> verifyStatusOfVoteSession(Long voteSessionId) {
        log.debug("call method verifyStatusOfVoteSession by Id: {} ", voteSessionId);
        return voteSessionDataRepository.existsByIdAndEnabledTrue(voteSessionId);
    }

    @Override
    public Mono<VoteSession> update(VoteSession voteSession) {
        log.debug("call method update of objet: {} ", voteSession);
        return voteSessionDataRepository.save(sqlMapper.toVoteSessionPost(voteSession)).map(sqlMapper::toVoteSessionEntity);
    }

    @Override
    public Flux<VoteSession> getAll() {
        return voteSessionDataRepository.findAll().map(sqlMapper::toVoteSessionEntity);
    }

}
