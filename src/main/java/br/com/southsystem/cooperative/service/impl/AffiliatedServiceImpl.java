package br.com.southsystem.cooperative.service.impl;


import br.com.southsystem.cooperative.domain.Affiliated;
import br.com.southsystem.cooperative.repository.AffiliatedRepository;
import br.com.southsystem.cooperative.service.AffiliatedService;
import br.com.southsystem.cooperative.service.dto.AffiliatedDTO;
import br.com.southsystem.cooperative.service.mapper.AffiliatedMapper;
import br.com.southsystem.cooperative.service.util.ParametersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class AffiliatedServiceImpl implements AffiliatedService {
    private final AffiliatedRepository affiliatedRepository;

    private final AffiliatedMapper affiliatedMapper;

    public AffiliatedServiceImpl(AffiliatedRepository affiliatedRepository, AffiliatedMapper affiliatedMapper) {
        this.affiliatedRepository = affiliatedRepository;
        this.affiliatedMapper = affiliatedMapper;
    }

    /**
     * Save a affiliated.
     *
     * @param affiliatedDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AffiliatedDTO save(AffiliatedDTO affiliatedDTO) {
        log.debug("Request to save Affiliated : {}", affiliatedDTO);
        Affiliated affiliated = affiliatedMapper.toEntity(affiliatedDTO);
        affiliated = affiliatedRepository.save(affiliated);
        return affiliatedMapper.toDto(affiliated);
    }

    /**
     * Get one affiliated by cpf.
     *
     * @param cpf the cpf of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AffiliatedDTO> findOneByCpf(String cpf) {
        log.debug("Request to get Affiliated : {}", cpf);
        return affiliatedRepository.findFirstByCpf(cpf)
                .map(affiliatedMapper::toDto);
    }

    /**
     * Get or create one affiliated by cpf.
     *
     * @param cpf the cpf of the entity.
     * @return the entity.
     */
    @Override
    public AffiliatedDTO findOrCreateByCpf(String cpf) {
        return this.findOneByCpf(cpf).orElseGet(() -> {
            AffiliatedDTO affiliatedDTO = new AffiliatedDTO();
            affiliatedDTO.setCpf(ParametersUtil.justDigits(cpf));
            return this.save(affiliatedDTO);
        });
    }

}
