package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.domain.enumeration.VoteType;
import br.com.southsystem.cooperative.exception.BadRequestAlertException;
import br.com.southsystem.cooperative.exception.CpfUnableToVoteException;
import br.com.southsystem.cooperative.repository.AffiliatedRepository;
import br.com.southsystem.cooperative.repository.SessionRepository;
import br.com.southsystem.cooperative.repository.SubjectRepository;
import br.com.southsystem.cooperative.repository.VoteRepository;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.SubjectService;
import br.com.southsystem.cooperative.service.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
    private AffiliatedRepository affiliatedRepository;

    @Autowired
    private VoteServiceImpl voteService;

    @DisplayName("Test create a vote in the data base with VoteType Sim")
    @Test
    void testCreateAVoteInTheDataBaseVoteTypeSim() throws Exception {
        try {
            var aSubject = getASubjectSaved();

            var aSessionSave = getASessionSaved(aSubject);
            var cpf = "26283451020";
            var vote = VoteCreateRequestDTO.builder()
                    .vote(VoteType.Sim)
                    .affiliatedCpf(cpf)
                    .sessionId(aSessionSave.getId())
                    .build();

            var voteSaved = voteService.vote(vote);

            Assertions.assertNotNull(voteSaved.getVote());
            Assertions.assertNotNull(voteSaved.getId());
            Assertions.assertEquals(voteSaved.getVote(), VoteType.Sim);
            Assertions.assertEquals(voteSaved.getAffiliatedCpf(), cpf);
            Assertions.assertNotNull(voteSaved.getVoteDateTime());
            Assertions.assertNotNull(voteSaved.getSessionId());


        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        }
        cleanDataBase();
    }

    @DisplayName("Test create a vote in the data base with VoteType Não")
    @Test
    void testCreateAVoteInTheDataBaseVoteTypeNao() throws Exception {

        try {
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
            Assertions.assertNotNull(voteSaved.getSessionId());


        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        }

        cleanDataBase();
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


        try {
            voteService.vote(vote);
        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        } catch (ConstraintViolationException e) {
            Assertions.assertEquals(e.getConstraintViolations().size(), 1);
        }

        cleanDataBase();

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
        try {
            voteService.vote(vote);
        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        } catch (ConstraintViolationException e) {
            Assertions.assertEquals(e.getConstraintViolations().size(), 2);
        }


        cleanDataBase();

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


        try {
            voteService.vote(vote);
        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        } catch (ConstraintViolationException e) {
            Assertions.assertEquals(e.getConstraintViolations().size(), 2);
        }

        cleanDataBase();

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


        try {
            voteService.vote(vote);
        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        } catch (ConstraintViolationException e) {
            Assertions.assertEquals(e.getConstraintViolations().size(), 1);
        }

        cleanDataBase();

    }

    @DisplayName("Test create a vote in the data base in same session two times")
    @Test
    void testCreateAVoteInTheDataBaseInSameSessionTwoTimes() throws Exception {
        try {
            var aSubject = getASubjectSaved();

            var aSessionSave = getASessionSaved(aSubject);
            var cpf = "26283451020";
            var vote = VoteCreateRequestDTO.builder()
                    .vote(VoteType.Não)
                    .affiliatedCpf(cpf)
                    .sessionId(aSessionSave.getId())
                    .build();
            voteService.vote(vote);
            voteService.vote(vote);

        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        } catch (BadRequestAlertException e) {
            Assertions.assertEquals(e.getMessage(), "The affiliated has benn voted in the session!");
        }

        cleanDataBase();
    }

    @DisplayName("Test create a vote in the data base in non-existent session")
    @Test
    void testCreateAVoteInTheDataBaseInNonExistentSession() throws Exception {
        try {

            var cpf = "26283451020";
            var vote = VoteCreateRequestDTO.builder()
                    .vote(VoteType.Não)
                    .affiliatedCpf(cpf)
                    .sessionId(0L)
                    .build();
            voteService.vote(vote);

        } catch (CpfUnableToVoteException e) {
            Assertions.assertEquals(e.getMessage(), "The CPF is unable to vote.");
        } catch (EntityNotFoundException e) {
            Assertions.assertEquals(e.getMessage(), "The Session does not exist!");
        }


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

    public void cleanDataBase() {
        voteRepository.deleteAll();
        affiliatedRepository.deleteAll();
        sessionRepository.deleteAll();
        subjectRepository.deleteAll();
    }
}
