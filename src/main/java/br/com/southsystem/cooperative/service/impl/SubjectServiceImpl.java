package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.domain.Subject;

import br.com.southsystem.cooperative.domain.Vote;
import br.com.southsystem.cooperative.domain.enumeration.VoteType;
import br.com.southsystem.cooperative.exception.BadRequestAlertException;
import br.com.southsystem.cooperative.repository.SubjectRepository;
import br.com.southsystem.cooperative.service.SubjectService;
import br.com.southsystem.cooperative.service.dto.SubjectCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.SubjectDTO;

import br.com.southsystem.cooperative.service.dto.SubjectResultDTO;
import br.com.southsystem.cooperative.service.mapper.SubjectMapper;
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
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    /**
     * Save a subject.
     *
     * @param subjectDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubjectDTO save(SubjectCreateRequestDTO subjectDTO) {
        log.debug("Request to save Subject : {}", subjectDTO);
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject.setDateTime(LocalDateTime.now());
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    /**
     * Get all the subjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SubjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Subjects");
        return subjectRepository.findAll(pageable)
                .map(subjectMapper::toDto);
    }

    /**
     * Get one subject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectDTO> findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        return subjectRepository.findById(id)
                .map(subjectMapper::toDto);
    }
    /**
     * Get one subject by id.
     *
     * @param id the id of the domain.
     * @return the domain.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Subject> getDomainSubject(Long id) {
        log.debug("Request to get Subject : {}", id);
        return subjectRepository.findById(id);
    }
    /**
     * Get the voting result of the subject by id.
     *
     * @param id the id of the subject.
     */
    @Override
    public SubjectResultDTO votingResult(Long id) {
        log.debug("Request to voting result by subject id: {}", id);
        Subject subject = getDomainSubject(id).orElseThrow(() -> new EntityNotFoundException("The Subject does not exist!"));
        if (subject.getSession() == null) {
            throw new BadRequestAlertException("The session of this subject was not opened!");
        }
        SubjectResultDTO subjectResultDTO = subjectMapper.toResultDto(subject);
        List<Vote> votes = subject.getSession().getVotes();
        subjectResultDTO.setYesVotes(votes.stream().filter(vote -> vote.getVote().equals(VoteType.Sim)).count());
        subjectResultDTO.setNoVotes(votes.size() - subjectResultDTO.getYesVotes());
        return subjectResultDTO;
    }

}
