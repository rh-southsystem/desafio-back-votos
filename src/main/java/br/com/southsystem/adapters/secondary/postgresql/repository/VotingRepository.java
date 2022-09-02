package br.com.southsystem.adapters.secondary.postgresql.repository;

import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.VotingDataRepository;
import br.com.southsystem.application.domain.entity.Voting;
import br.com.southsystem.application.port.secondary.ResultVoteSessionSecondaryRepositoryPort;
import br.com.southsystem.application.port.secondary.VotingSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class VotingRepository implements VotingSecondaryRepositoryPort, ResultVoteSessionSecondaryRepositoryPort {

    private final VotingDataRepository votingDataRepository;
    private final PostgreSqlMapper mapper;


    @Override
    public Mono<Voting> findById(Long votingId) {
        log.debug("call method findById by Id: {} ", votingId);
        return votingDataRepository.findById(votingId).map(mapper::toVotingEntity);

    }

    @Override
    public Mono<Boolean> existsByVoteSessionIdAndAssociateId(Long voteSessionId, Long associateId) {
        log.debug("call method existsByVoteSessionIdAndAssociateId by voteSessionId: {} and  associateId {}", voteSessionId, associateId);
        return votingDataRepository.existsByVoteSessionIdAndAssociateId(voteSessionId, associateId);
    }

    @Override
    public Mono<Voting> save(Voting voting) {
        log.debug("call method save by objet: {} ",  voting);
        return votingDataRepository.save(mapper.toVotingPost(voting)).map(mapper::toVotingEntity);
    }

    @Override
    public Flux<Voting> findAllByVoteSessionId(Long voteSessionId) {
        log.debug("call method findAllByVoteSessionId by id: {} ",  voteSessionId);
        return votingDataRepository.findAllByVoteSessionId(voteSessionId).map(mapper::toVotingEntity);
    }
}
