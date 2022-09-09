package br.com.southsystem.application.port.primary;

import br.com.southsystem.application.domain.entity.Associate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssociatePrimaryPort {
    Mono<Associate> findByCpf(String cpf);

    Flux<Associate> getAll();
}
