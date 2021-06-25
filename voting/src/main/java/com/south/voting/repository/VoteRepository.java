package com.south.voting.repository;

import com.south.voting.domain.AssociateEntity;
import com.south.voting.domain.SessionEntity;
import com.south.voting.domain.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteRepository extends JpaRepository<VoteEntity,Long> {
    boolean existsByAssociateAndSession(AssociateEntity associateEntity, SessionEntity sessionEntity);
}
