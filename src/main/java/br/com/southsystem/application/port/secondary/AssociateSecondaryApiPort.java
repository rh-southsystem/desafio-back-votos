package br.com.southsystem.application.port.secondary;

import reactor.core.publisher.Mono;

public interface AssociateSecondaryApiPort {
    Mono<Boolean> verifyStatusVoteAssociateByCpf(String associateCpf);
}
