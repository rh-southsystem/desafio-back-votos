package com.south.voting.service;

import com.south.voting.domain.TopicEntity;
import com.south.voting.domain.ResultTopic;

import java.util.Optional;

public interface TopicService {
    TopicEntity save(TopicEntity topicEntity);
    Optional<TopicEntity> findById(Long id);
    ResultTopic getResultTopic(Long idTopic) throws Exception;
}
