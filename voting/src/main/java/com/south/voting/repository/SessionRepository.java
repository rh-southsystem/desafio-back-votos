package com.south.voting.repository;

import com.south.voting.domain.SessionEntity;
import com.south.voting.domain.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Long> {
    boolean existsByTopic(TopicEntity topicEntity);
    Optional<SessionEntity> findByTopic(TopicEntity topicEntity);
}
