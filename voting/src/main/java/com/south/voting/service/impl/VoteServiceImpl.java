package com.south.voting.service.impl;

import com.south.voting.domain.AssociateEntity;
import com.south.voting.domain.SessionEntity;
import com.south.voting.domain.StatusDocument;
import com.south.voting.domain.VoteEntity;
import com.south.voting.dto.VoteDTO;
import com.south.voting.enums.StatusEnum;
import com.south.voting.repository.VoteRepository;
import com.south.voting.service.AssociateService;
import com.south.voting.service.SessionService;
import com.south.voting.service.VoteService;
import com.south.voting.util.AssociateRest;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteServiceImpl.class);
    
    private final VoteRepository voteRepository;

    private final SessionService sessionService;

    private final AssociateService associateService;

    private final AssociateRest associateRest;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, SessionService sessionService, AssociateService associateService, AssociateRest associateRest) {
        this.voteRepository   = voteRepository;
        this.sessionService   = sessionService;
        this.associateService = associateService;
        this.associateRest    = associateRest;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public VoteEntity save(VoteDTO voteDTO) throws Exception {
        validateVote(voteDTO);

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteEnum(voteDTO.getVoteEnum());

        Optional<SessionEntity> sessionEntityOptional = getSession(voteDTO.getIdSession());
        checkIfSessionExist(sessionEntityOptional);

        SessionEntity sessionEntity = sessionEntityOptional.get();
        checkSessionDateValid(sessionEntity.getExpiration());
        voteEntity.setSession(sessionEntity);

        Optional<AssociateEntity> associateOptional = getAssociate(voteDTO.getIdAssociate());
        checkIfAssociateExist(associateOptional);

        AssociateEntity associateEntity = associateOptional.get();
        checkIfDocumentAssociateValid(associateEntity.getDocument());
        checkIfAssociateVoted(associateEntity,sessionEntity);
        voteEntity.setAssociate(associateEntity);

        return voteRepository.save(voteEntity);
    }

    private void validateVote(VoteDTO voteDTO) {
        LOGGER.info("Validando dados de voto");
        Validate.notNull(voteDTO,"Necessário informar o voto.");
        Validate.notNull(voteDTO.getVoteEnum(),"Necessário informar o valor do voto.");
        Validate.notNull(voteDTO.getIdSession(),"Necessário informar a sessão.");
        Validate.notNull(voteDTO.getIdAssociate(),"Necessário informar o associado.");
    }

    private Optional<SessionEntity> getSession(Long idSession){
       return sessionService.findById(idSession);
    }

    private void checkIfSessionExist(Optional<SessionEntity> sessionEntityOptional) throws Exception {
       LOGGER.info("Verificando se a sessão informada foi encontrada");
       if(sessionEntityOptional.isEmpty()){
          throw new Exception("Sessão informada não encontrada.");
       }
    }

    private Optional<AssociateEntity> getAssociate(Long idAssociate){
       return associateService.findByid(idAssociate);
    }

    private void checkIfAssociateExist(Optional<AssociateEntity> associateEntityOptional) throws Exception {
      LOGGER.info("Verificando se o associado existe.");
      if(associateEntityOptional.isEmpty()) {
         throw new Exception("Associado informado não encontrado.");
      }
    }

    private void checkSessionDateValid(LocalDateTime dateSession) throws Exception {
        LOGGER.info("Verificando se a data está na validade.");
        if(dateSession.isBefore(LocalDateTime.now())){
           throw new Exception("Para esta sessão o tempo já está expirado.");
        }
    }

    private void checkIfAssociateVoted(AssociateEntity associateEntity,SessionEntity sessionEntity) throws Exception {
        LOGGER.info("Verificando se o associado já votou na sessão informada.");
        boolean exists = voteRepository.existsByAssociateAndSession(associateEntity,sessionEntity);
        if(exists) {
           throw new Exception("Associado já votou nesta sessão.");
        }
    }

    private void checkIfDocumentAssociateValid(String document) throws Exception {
        LOGGER.info("Verificando se o associado está com documento valido.");
        StatusDocument statusDocument = associateRest.validateDocumentAssociate(document);
        if(statusDocument.getStatus().equals(StatusEnum.UNABLE_TO_VOTE)) {
            throw new Exception("Associado não pode votar por conta do documento.");
        }
    }


}
