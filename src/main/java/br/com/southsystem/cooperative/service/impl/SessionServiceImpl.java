package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.domain.Session;

import br.com.southsystem.cooperative.exception.BadRequestAlertException;
import br.com.southsystem.cooperative.repository.SessionRepository;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.SubjectService;
import br.com.southsystem.cooperative.service.dto.SessionDTO;

import br.com.southsystem.cooperative.service.dto.SessionInitRequestDTO;
import br.com.southsystem.cooperative.service.mapper.SessionMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;

    private final SubjectService subjectService;


    public SessionServiceImpl(SessionRepository sessionRepository, SessionMapper sessionMapper, SubjectService subjectService) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.subjectService = subjectService;

    }

    /**
     * Save a session.
     *
     * @param sessionInitRequestDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SessionDTO init(SessionInitRequestDTO sessionInitRequestDTO) {
        log.debug("Request to save Session : {}", sessionInitRequestDTO);
        subjectService.findOne(sessionInitRequestDTO.getSubjectId()).orElseThrow(() -> new EntityNotFoundException("The Subject does not exist!"));
        findOneBySubjectId(sessionInitRequestDTO.getSubjectId())
                .ifPresent((s) -> new BadRequestAlertException("The session has already been started"));

        SessionDTO sessionDTO = sessionMapper.toSessionInitRequestDto(sessionInitRequestDTO);
        sessionDTO.setStartDateTime(LocalDateTime.now());
        Session session = sessionMapper.toEntity(sessionDTO);
        session = sessionRepository.save(session);
        return sessionMapper.toDto(session);
    }

    /**
     * Get one session by id Subject.
     *
     * @param subjectId the cpf of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SessionDTO> findOneBySubjectId(Long subjectId) {
        log.debug("Request to get session by id subject : {}", subjectId);
        return sessionRepository.findFirstBySubjectId(subjectId)
                .map(sessionMapper::toDto);
    }

    /**
     * Get all the sessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sessions");
        return sessionRepository.findAll(pageable)
                .map(sessionMapper::toDto);
    }


    /**
     * Get one session by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SessionDTO> findOne(Long id) {
        log.debug("Request to get Session : {}", id);
        return sessionRepository.findById(id)
                .map(sessionMapper::toDto);
    }


}

