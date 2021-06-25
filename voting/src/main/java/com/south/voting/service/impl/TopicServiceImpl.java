package com.south.voting.service.impl;

import com.south.voting.domain.SessionEntity;
import com.south.voting.domain.TopicEntity;
import com.south.voting.domain.VoteEntity;
import com.south.voting.domain.ResultTopic;
import com.south.voting.enums.VoteEnum;
import com.south.voting.repository.TopicRepository;
import com.south.voting.service.SessionService;
import com.south.voting.service.TopicService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    private final SessionService sessionService;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository,@Lazy SessionService sessionService) {
       this.topicRepository = topicRepository;
       this.sessionService = sessionService;
    }

    @Override
    @Transactional
    public TopicEntity save(TopicEntity topicEntity) {
       validateTopic(topicEntity);
       return topicRepository.save(topicEntity);
    }

    private void validateTopic(TopicEntity topicEntity){
       Validate.notNull(topicEntity,"Informe o tópico.");
       Validate.notNull(topicEntity.getName(),"Nome do tópico é obrigatório.");
       Validate.notBlank(topicEntity.getName(),"Nome do topico não pode ser vazio.");
    }
    @Override
    public Optional<TopicEntity> findById(Long id) {
        return topicRepository.findById(id);
    }

    @Override
    public ResultTopic getResultTopic(Long idTopic) throws Exception {
        Optional<TopicEntity> topicEntityOptional = findById(idTopic);
        checkIfExistTopic(topicEntityOptional);

        TopicEntity topicEntity = topicEntityOptional.get();

        Optional<SessionEntity> sessionOptional = getSessionByTopic(topicEntity);
        checkIfExistSession(sessionOptional);

        final SessionEntity sessionEntity = sessionOptional.get();

        ResultTopic resultTopic = new ResultTopic();

        Long countVotes    = countVotes(sessionEntity.getVotes());
        Long countVotesYes = countVotesYes(sessionEntity.getVotes());
        Long countVotesNo  = countVotesNo(sessionEntity.getVotes());

        resultTopic.setCountVotes(countVotes);
        resultTopic.setCountVotesYes(countVotesYes);
        resultTopic.setCountVotesNo(countVotesNo);
        resultTopic.setTopicEntity(topicEntity);

        return resultTopic;
    }

    private void checkIfExistTopic(Optional<TopicEntity> topicEntityOptional) throws Exception {
       LOGGER.info("Verificando se o topico existe.");
       if(topicEntityOptional.isEmpty()) {
          throw new Exception("Tópico informado não encontrado.");
       }
    }

    private Optional<SessionEntity> getSessionByTopic(TopicEntity topicEntity) {
       return sessionService.findByTopic(topicEntity);
    }

    private void checkIfExistSession(Optional<SessionEntity> sessionEntityOptional) throws Exception {
       LOGGER.info("Verificando se existe sessão para apuração do topico.");
       if(sessionEntityOptional.isEmpty()){
          throw new Exception("Sessão não encontrada para este tópico.");
       }
    }

    private Long countVotes(Set<VoteEntity> votes) {
       return votes.stream().count();
    }

    private Long countVotesYes(Set<VoteEntity> votes){
       return votes.stream()
                   .filter(voteEntity -> voteEntity.getVoteEnum().equals(VoteEnum.SIM))
                   .count();
    }

    private Long countVotesNo(Set<VoteEntity> votes){
        return votes.stream()
                .filter(voteEntity -> voteEntity.getVoteEnum().equals(VoteEnum.NAO))
                .count();
    }



}
