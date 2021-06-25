package com.south.voting.repository;

import com.south.voting.domain.AssociateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociateRepository extends JpaRepository<AssociateEntity,Long> {
    Optional<AssociateEntity> findByDocument(String document);
}
