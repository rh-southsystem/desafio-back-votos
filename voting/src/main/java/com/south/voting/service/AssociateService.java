package com.south.voting.service;

import com.south.voting.domain.AssociateEntity;

import java.util.Optional;

public interface AssociateService {
    AssociateEntity save(AssociateEntity associateEntity) throws Exception;
    Optional<AssociateEntity> findByid(Long id);
}
