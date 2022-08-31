package br.com.southsystem.application.port.secondary;

import br.com.southsystem.application.domain.entity.Associate;
import reactor.core.publisher.Mono;

public interface AssociateSecondaryRepositoryPort {
    Mono<Associate> findByCpf(String cpfAssociate);
    Mono<Associate> findById(Long associateId);

    Mono<Boolean> existsById(Long associateId);
}
