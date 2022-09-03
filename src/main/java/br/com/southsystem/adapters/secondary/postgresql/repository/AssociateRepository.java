package br.com.southsystem.adapters.secondary.postgresql.repository;

import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.AssociateDataRepository;
import br.com.southsystem.application.domain.entity.Associate;
import br.com.southsystem.application.port.secondary.AssociateSecondaryRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Log4j2
@Component
@RequiredArgsConstructor
public class AssociateRepository implements AssociateSecondaryRepositoryPort {

    private final AssociateDataRepository associateDataRepository;
    private final PostgreSqlMapper mapper;

    @Override
    public Mono<Associate> findByCpf(String cpfAssociate) {
        log.debug("call method findByCpf by cpf: {} ", cpfAssociate);
        return associateDataRepository.findByCpf(cpfAssociate).map(mapper::toAssociateEntity);
    }

    @Override
    public Mono<Associate> findById(Long associateId) {
        log.debug("call method findById by id: {} ", associateId);
        return associateDataRepository.findById(associateId).map(mapper::toAssociateEntity);
    }

    @Override
    public Mono<Boolean> existsById(Long associateId) {
        log.debug("call method existsById by id: {} ", associateId);
        return associateDataRepository.existsById(associateId);
    }

    @Override
    public Flux<Associate> getAll() {
        log.debug("call method getAll ");
        return associateDataRepository.findAll().map(mapper::toAssociateEntity);
    }
}
