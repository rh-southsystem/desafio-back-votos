package br.com.southsystem.application.service;

import br.com.southsystem.application.domain.entity.Voting;
import br.com.southsystem.application.exception.VotingNotFoundException;
import br.com.southsystem.application.port.primary.VotingPrimaryPort;
import br.com.southsystem.application.port.secondary.AssociateSecondaryApiPort;
import br.com.southsystem.application.port.secondary.AssociateSecondaryRepositoryPort;
import br.com.southsystem.application.port.secondary.VoteSessionSecondaryRepositoryPort;
import br.com.southsystem.application.port.secondary.VotingSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VotingService implements VotingPrimaryPort {

    private final VotingSecondaryRepositoryPort secondaryRepositoryPort;
    private final AssociateSecondaryRepositoryPort associateRepositoryPort;
    private final VoteSessionSecondaryRepositoryPort voteSessionRepositoryPort;

    private final AssociateSecondaryApiPort secondaryApiPort;

    @Override
    public Mono<Voting> save(Voting voting) {
        return verifyExistence(voting)
                    .then(verifyAssociate(voting))
                    .then(verifyPowerVoteAssociate(voting))
                    .then(verifyVoteSession(voting))
                    .then(verifyStatusVoteSession(voting))
                    .then(secondaryRepositoryPort.save(voting).flatMap(this::loadRelations));
    }

    @Override
    public Mono<Voting> findById(Long votingId) {
        return secondaryRepositoryPort.findById(votingId)
                .switchIfEmpty(Mono.error(VotingNotFoundException::new))
                .flatMap(this::loadRelations);
    }

    private Mono<Boolean> verifyPowerVoteAssociate(Voting voting) {
        return associateRepositoryPort.findById(voting.getAssociate().getId())
                .flatMap(associate -> secondaryApiPort.verifyStatusVoteAssociateByCpf(associate.getCpf()))
                .handle((isEnable, sink) -> {
                    if(Boolean.FALSE.equals(isEnable)){
                        sink.error(new IllegalArgumentException("Associate cannot vote"));
                    } else {
                        sink.next(isEnable);
                    }
                });
    }
    private Mono<Boolean> verifyStatusVoteSession(Voting voting) {
        return voteSessionRepositoryPort
                .verifyStatusOfVoteSession(voting.getVoteSession().getId())
                .handle((isClose, sink) -> {
                    if(Boolean.FALSE.equals(isClose)){
                        sink.error(new IllegalArgumentException("The date of session vote expired"));
                    } else {
                        sink.next(isClose);
                    }
                });
    }
    private Mono<Boolean> verifyAssociate(final Voting voting) {
        return associateRepositoryPort
                .existsById(voting.getAssociate().getId())
                .handle((exists, sink) -> {
                    if(Boolean.FALSE.equals(exists)){
                        sink.error(new IllegalArgumentException("Associate not found"));
                    } else {
                        sink.next(exists);
                    }
                });
    }

    private Mono<Boolean> verifyVoteSession(final Voting voting) {
        return voteSessionRepositoryPort
                .existsById(voting.getVoteSession().getId())
                .handle((exists, sink) -> {
                    if(Boolean.FALSE.equals(exists)){
                        sink.error(new IllegalArgumentException("Vote Session not found"));
                    } else {
                        sink.next(exists);
                    }
                });
    }

    private Mono<Boolean> verifyExistence(final Voting voting) {
        return secondaryRepositoryPort
                .existsByVoteSessionIdAndAssociateId(voting.getVoteSession().getId(), voting.getAssociate().getId())
                .handle((exists, sink) -> {
                    if(Boolean.TRUE.equals(exists)){
                        sink.error(new IllegalArgumentException("This vote was previously registered"));
                    } else {
                        sink.next(!exists);
                    }
                });
    }

    private Mono<Voting> loadRelations(final Voting voting) {
        Mono<Voting> votingMono = Mono.just(voting);

        // Load the associate (if set)
        if(voting.getAssociate().getId() != null) {
            votingMono =  votingMono.zipWith(associateRepositoryPort.findById(voting.getAssociate().getId()))
                    .map(r -> {
                        r.getT1().setAssociate(r.getT2());
                        return r.getT1();
                    });
        }

        // Load the Vote Session (if set)
        if(voting.getVoteSession().getId() != null) {
            votingMono =  votingMono.zipWith(voteSessionRepositoryPort.findById(voting.getVoteSession().getId()))
                    .map(r -> {
                        r.getT1().setVoteSession(r.getT2());
                        return r.getT1();
                    });
        }
        return  votingMono;
    }
}
