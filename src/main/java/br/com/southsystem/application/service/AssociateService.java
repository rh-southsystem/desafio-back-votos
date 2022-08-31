package br.com.southsystem.application.service;

import br.com.southsystem.application.domain.entity.Associate;
import br.com.southsystem.application.exception.AssociateNotFoundException;
import br.com.southsystem.application.port.primary.AssociatePrimaryPort;
import br.com.southsystem.application.port.secondary.AssociateSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AssociateService implements AssociatePrimaryPort {

    private final String REGEX_NUMBER_ONLY = "[^0-9]";
    private final String SWAP_FOR = "";

    private final AssociateSecondaryRepositoryPort associateSecondaryRepositoryPort;

    @Override
    public Mono<Associate> findByCpf(String cpfAssociate) {
        return Mono.just(cpfAssociate)
                .map(c -> c.replaceAll(REGEX_NUMBER_ONLY, SWAP_FOR))
                .flatMap(associateSecondaryRepositoryPort::findByCpf)
                .switchIfEmpty(Mono.error(new AssociateNotFoundException(cpfAssociate)));
    }
}
