package br.com.southsystem.adapters.secondary.postgresql.repository.data;

import br.com.southsystem.adapters.secondary.postgresql.entity.AssociatePost;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository
public interface AssociateDataRepository extends ReactiveCrudRepository<AssociatePost, Long> {
    Mono<AssociatePost> findByCpf(String cpf);
}
