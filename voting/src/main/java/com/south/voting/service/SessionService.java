package com.south.voting.service;

import com.south.voting.domain.SessionEntity;
import com.south.voting.domain.TopicEntity;
import com.south.voting.dto.SessionDTO;

import java.util.Optional;

public interface SessionService {
    SessionEntity save(SessionDTO sessionDTO) throws Exception;
    Optional<SessionEntity> findById(Long id);
    Optional<SessionEntity> findByTopic(TopicEntity topicEntity);
}
