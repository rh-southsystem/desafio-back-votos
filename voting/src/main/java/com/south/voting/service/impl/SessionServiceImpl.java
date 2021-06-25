package com.south.voting.service.impl;

import com.south.voting.domain.SessionEntity;
import com.south.voting.domain.TopicEntity;
import com.south.voting.dto.SessionDTO;
import com.south.voting.repository.SessionRepository;
import com.south.voting.service.SessionService;
import com.south.voting.service.TopicService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final SessionRepository sessionRepository;

    private final TopicService topicService;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, TopicService topicService) {
       this.sessionRepository = sessionRepository;
       this.topicService = topicService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SessionEntity save(SessionDTO sessionDTO) throws Exception {
        validateSession(sessionDTO);
        SessionEntity sessionEntity = SessionDTO.fromToSessionEntity(sessionDTO);

        Optional<TopicEntity> topicOptional = getTopicSession(sessionDTO.getIdTopic());
        validateTopicExist(topicOptional);
        TopicEntity topicEntity = topicOptional.get();
        checkIfTopicContainsSession(topicEntity);
        sessionEntity.setTopic(topicEntity);
        return sessionRepository.save(sessionEntity);
    }

    private void validateSession(SessionDTO sessionDTO) {
        LOGGER.info("Validando informações da sessão.");
        Validate.notNull(sessionDTO,"Necessário informar a sessão.");
        Validate.notNull(sessionDTO.getIdTopic(),"Necessário informar qual o tópico da sessão.");
    }

    private Optional<TopicEntity> getTopicSession(Long id) throws Exception {
       return topicService.findById(id);
    }

    private void validateTopicExist(Optional<TopicEntity> topicEntityOptional) throws Exception {
       LOGGER.info("Validando se o topico está cadastrado.");
       if(topicEntityOptional.isEmpty()){
          throw new Exception("Topico informado não encontrado.");
       }
    }

    private void checkIfTopicContainsSession (TopicEntity topicEntity) throws Exception {
        LOGGER.info("Validando se existe sessão para o topico informado.");
        boolean existTopic = sessionRepository.existsByTopic(topicEntity);
        if(existTopic) {
           throw new Exception("Este tópico já contém uma sessão.");
        }
    }

    @Override
    public Optional<SessionEntity> findById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Optional<SessionEntity> findByTopic(TopicEntity topicEntity) {
        return sessionRepository.findByTopic(topicEntity);
    }

}
