package br.com.southsystem.adapters.secondary.postgresql.repository;

import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.VotingDataRepository;
import br.com.southsystem.application.domain.entity.Voting;
import br.com.southsystem.application.port.secondary.ResultVoteSessionSecondaryRepositoryPort;
import br.com.southsystem.application.port.secondary.VotingSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VotingRepository implements VotingSecondaryRepositoryPort, ResultVoteSessionSecondaryRepositoryPort {

    private final VotingDataRepository votingDataRepository;
    private final PostgreSqlMapper mapper;


    @Override
    public Mono<Voting> findById(Long votingId) {
        return votingDataRepository.findById(votingId).map(mapper::toVotingEntity);

    }

    @Override
    public Mono<Boolean> existsByVoteSessionIdAndAssociateId(Long voteSessionId, Long associateId) {
        return votingDataRepository.existsByVoteSessionIdAndAssociateId(voteSessionId, associateId);
    }

    @Override
    public Mono<Voting> save(Voting voting) {
        return votingDataRepository.save(mapper.toVotingPost(voting)).map(mapper::toVotingEntity);
    }

    @Override
    public Flux<Voting> findAllByVoteSessionId(Long voteSessionId) {
        return votingDataRepository.findAllByVoteSessionId(voteSessionId).map(mapper::toVotingEntity);
    }
}
