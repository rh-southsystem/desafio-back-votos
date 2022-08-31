package br.com.southsystem.application.service;

import br.com.southsystem.application.domain.entity.ResultVoteSession;
import br.com.southsystem.application.domain.entity.VoteSession;
import br.com.southsystem.application.domain.entity.enums.VoteType;
import br.com.southsystem.application.exception.VoteSessionExistsException;
import br.com.southsystem.application.exception.VoteSessionNotFoundException;
import br.com.southsystem.application.port.primary.VoteSessionPrimaryPort;
import br.com.southsystem.application.port.secondary.ResultVoteSessionSecondaryRepositoryPort;
import br.com.southsystem.application.port.secondary.VoteSessionSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VoteSessionService implements VoteSessionPrimaryPort {

    private final VoteSessionSecondaryRepositoryPort secondaryRepositoryPort;
    private final ResultVoteSessionSecondaryRepositoryPort resultVoteSessionSecondaryRepositoryPort;

    @Override
    public Mono<VoteSession> findById(Long voteSessionId) {
        return secondaryRepositoryPort
                    .findById(voteSessionId)
                    .switchIfEmpty(Mono.error(new VoteSessionNotFoundException()));
    }

    @Override
    public Mono<VoteSession> findVoteSessionByIdWithResult(Long voteSessionId) {
        return secondaryRepositoryPort
                    .findById(voteSessionId)
                    .switchIfEmpty(Mono.error(new VoteSessionNotFoundException()))
                    .flatMap(this::loadsResults);
    }

    @Override
    public Mono<VoteSession> saveVoteSession(VoteSession voteSession) {
        return Mono.just(voteSession)
                .flatMap(this::verifyExists)
                .flatMap(secondaryRepositoryPort::save);
    }

    private Mono<VoteSession> verifyExists(VoteSession voteSession) {
     return secondaryRepositoryPort
             .existsByDescription(voteSession.getDescription())
                .handle((exists, sink) -> {
                    if(Boolean.TRUE.equals(exists)){
                        sink.error(new VoteSessionExistsException());
                    } else {
                        sink.next(voteSession);
                    }
                });
    }

    private Mono<VoteSession> loadsResults(VoteSession voteSession){
       return Mono.just(voteSession)
                    .handle((vs, sink) -> {
                        if(Boolean.TRUE.equals(vs.getEnabled())){
                            sink.error(new IllegalArgumentException("Vote Session in progress"));
                        } else {
                            sink.next(vs);
                        }
                    })
                    .map(o -> (VoteSession) o)
                    .zipWith(resultVoteSessionSecondaryRepositoryPort.findAllByVoteSessionId(voteSession.getId()).collectList())
                            .map(t -> {
                                Long scoreTotal = t.getT2().stream().count();
                                Long scoreFor = t.getT2().stream().filter(v-> v.getVoteType().equals(VoteType.YES)).count();
                                Long scoreAgainst = t.getT2().stream().filter(v-> v.getVoteType().equals(VoteType.NO)).count();

                                t.getT1().setResult(ResultVoteSession.of(scoreAgainst,scoreFor,scoreTotal));

                                return t.getT1();
                            });
    }
}
