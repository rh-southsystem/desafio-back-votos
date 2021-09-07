package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.domain.enumeration.VoteType;
import br.com.southsystem.cooperative.repository.SessionRepository;
import br.com.southsystem.cooperative.repository.SubjectRepository;
import br.com.southsystem.cooperative.repository.VoteRepository;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.SubjectService;
import br.com.southsystem.cooperative.service.VoteService;
import br.com.southsystem.cooperative.service.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
@SpringBootTest
@AutoConfigureMockMvc
public class VoteServiceImplTest {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @DisplayName("Test create a vote in the data base with VoteType Sim")
    @Test
    void testCreateAVoteInTheDataBaseVoteTypeSim() throws Exception {

        var aSubject = getASubjectSaved();

        var aSessionSave = getASessionSaved(aSubject);
        var cpf = "26283451020";
        var vote = VoteCreateRequestDTO.builder()
                .vote(VoteType.Não)
                .affiliatedCpf(cpf)
                .sessionId(aSessionSave.getId())
                .build();

        var voteSaved = voteService.vote(vote);

        Assertions.assertNotNull(voteSaved.getVote());
        Assertions.assertNotNull(voteSaved.getId());
        Assertions.assertEquals(voteSaved.getVote(), VoteType.Sim);
        Assertions.assertEquals(voteSaved.getAffiliatedCpf(), cpf);
        Assertions.assertNotNull(voteSaved.getVoteDateTime());
        Assertions.assertNull(voteSaved.getSessionId());

        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());
        voteRepository.deleteById(voteSaved.getId());
    }

    @DisplayName("Test create a vote in the data base with VoteType Não")
    @Test
    void testCreateAVoteInTheDataBaseVoteTypeNao() throws Exception {

        var aSubject = getASubjectSaved();

        var aSessionSave = getASessionSaved(aSubject);
        var cpf = "26283451020";
        var vote = VoteCreateRequestDTO.builder()
                .vote(VoteType.Não)
                .affiliatedCpf(cpf)
                .sessionId(aSessionSave.getId())
                .build();

        var voteSaved = voteService.vote(vote);

        Assertions.assertNotNull(voteSaved.getVote());
        Assertions.assertNotNull(voteSaved.getId());
        Assertions.assertEquals(voteSaved.getVote(), VoteType.Não);
        Assertions.assertEquals(voteSaved.getAffiliatedCpf(), cpf);
        Assertions.assertNotNull(voteSaved.getVoteDateTime());
        Assertions.assertNull(voteSaved.getSessionId());

        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());
        voteRepository.deleteById(voteSaved.getId());
    }

    @DisplayName("Test create a vote in the data base without VoteType")
    @Test
    void testCreateAVoteInTheDataBaseWithoutVoteType() throws Exception {

        var aSubject = getASubjectSaved();

        var aSessionSave = getASessionSaved(aSubject);
        var cpf = "26283451020";
        var vote = VoteCreateRequestDTO.builder()
                .affiliatedCpf(cpf)
                .sessionId(aSessionSave.getId())
                .build();


        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> voteService.vote(vote)
        );
        Assertions.assertEquals(thrown.getConstraintViolations().size(),1);

        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());

    }

    @DisplayName("Test create a vote in the data base without cpf")
    @Test
    void testCreateAVoteInTheDataBaseWithoutCpf() throws Exception {

        var aSubject = getASubjectSaved();

        var aSessionSave = getASessionSaved(aSubject);
        String cpf = null;
        var vote = VoteCreateRequestDTO.builder()
                .affiliatedCpf(cpf)
                .vote(VoteType.Sim)
                .sessionId(aSessionSave.getId())
                .build();


        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> voteService.vote(vote)
        );
        Assertions.assertEquals(thrown.getConstraintViolations().size(),2);

        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());

    }

    @DisplayName("Test create a vote in the data base without cpf")
    @Test
    void testCreateAVoteInTheDataBaseWithoutVoteTypeAndCpf() throws Exception {

        var aSubject = getASubjectSaved();

        var aSessionSave = getASessionSaved(aSubject);
        String cpf = null;
        var vote = VoteCreateRequestDTO.builder()
                .affiliatedCpf(cpf)
                .sessionId(aSessionSave.getId())
                .build();


        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> voteService.vote(vote)
        );
        Assertions.assertEquals(thrown.getConstraintViolations().size(),2);

        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());

    }

    @DisplayName("Test create a vote in the data base with invalid cpf")
    @Test
    void testCreateAVoteInTheDataBaseWithInvalidCpf() throws Exception {

        var aSubject = getASubjectSaved();

        var aSessionSave = getASessionSaved(aSubject);
        String cpf = "000";
        var vote = VoteCreateRequestDTO.builder()
                .affiliatedCpf(cpf)
                .sessionId(aSessionSave.getId())
                .build();


        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> voteService.vote(vote)
        );
        Assertions.assertEquals(thrown.getConstraintViolations().size(),1);

        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());

    }

    private SubjectDTO getASubjectSaved() {
        var subjectCreateRequestDTO = SubjectCreateRequestDTO.builder()
                .subject("One subject").build();
        return subjectService.save(subjectCreateRequestDTO);
    }

    private SessionDTO getASessionSaved(SubjectDTO aSubject) {
        var currentDate = LocalDateTime.now();
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .endDateTime(currentDate.plusMinutes(60))
                .subjectId(aSubject.getId())
                .build();

        return sessionService.init(sessionCreateRequestDTO);
    }
}
