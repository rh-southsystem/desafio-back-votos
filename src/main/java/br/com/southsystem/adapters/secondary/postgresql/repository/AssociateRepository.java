package br.com.southsystem.adapters.secondary.postgresql.repository;

import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.AssociateDataRepository;
import br.com.southsystem.application.domain.entity.Associate;
import br.com.southsystem.application.port.secondary.AssociateSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AssociateRepository implements AssociateSecondaryRepositoryPort {

    private final AssociateDataRepository associateDataRepository;
    private final PostgreSqlMapper mapper;

    @Override
    public Mono<Associate> findByCpf(String cpfAssociate) {
        return associateDataRepository.findByCpf(cpfAssociate).map(mapper::toAssociateEntity);
    }

    @Override
    public Mono<Associate> findById(Long associateId) {
        return associateDataRepository.findById(associateId).map(mapper::toAssociateEntity);
    }

    @Override
    public Mono<Boolean> existsById(Long associateId) {
        return associateDataRepository.existsById(associateId);
    }
}
