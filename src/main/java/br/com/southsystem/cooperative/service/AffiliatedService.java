package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.service.dto.AffiliatedDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AffiliatedService {
    /**
     * Save an affiliated.
     *
     * @param affiliatedDTO the entity to save.
     * @return the persisted entity.
     */
    AffiliatedDTO save(AffiliatedDTO affiliatedDTO);

    /**
     * Get the "id" affiliated.
     *
     * @param cpf the cpf of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    Optional<AffiliatedDTO> findOneByCpf(String cpf);

    /**
     * Get or create one affiliated by cpf.
     *
     * @param cpf the cpf of the entity.
     * @return the entity.
     */
    AffiliatedDTO findOrCreateByCpf(String cpf);
}
