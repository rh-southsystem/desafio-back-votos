package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.service.dto.SessionDTO;
import br.com.southsystem.cooperative.service.dto.SessionInitRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface SessionService {
    /**
     * Init a session.
     *
     * @param sessionInitRequestDTO the entity to init.
     * @return the persisted entity.
     */
    SessionDTO init(SessionInitRequestDTO sessionInitRequestDTO);

    @Transactional(readOnly = true)
    Optional<SessionDTO> findOneBySubjectId(Long subjectId);

    /**
     * Get all the sessions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    Page<SessionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" session.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    Optional<SessionDTO> findOne(Long id);

    void isOpen(Long id);

    /**
     * This method check closed Sessions and send messages to topic.
     */
    void checkClosedSessionsAndSendMessageToTopic() throws JsonProcessingException;
}
