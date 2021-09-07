package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.exception.BadRequestAlertException;
import br.com.southsystem.cooperative.repository.SessionRepository;
import br.com.southsystem.cooperative.repository.SubjectRepository;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.SubjectService;
import br.com.southsystem.cooperative.service.dto.SessionDTO;
import br.com.southsystem.cooperative.service.dto.SessionInitRequestDTO;
import br.com.southsystem.cooperative.service.dto.SubjectCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.SubjectDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
public class SessionServiceImplTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @DisplayName("Test save a Session in the data base")
    @Test
    void testSaveASessionInTheDataBase() throws Exception {

        var aSubject = getASubjectSaved();
        var currentDate = LocalDateTime.now();
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .endDateTime(currentDate.plusMinutes(60))
                .subjectId(aSubject.getId())
                .build();

        var aSessionSave = sessionService.init(sessionCreateRequestDTO);

        Assertions.assertNotNull(aSessionSave.getStartDateTime());
        Assertions.assertNotNull(aSessionSave.getEndDateTime());
        Assertions.assertEquals(aSessionSave.getEndDateTime(), currentDate.plusMinutes(60));
        Assertions.assertNotNull(aSessionSave.getId());
        Assertions.assertNull(aSessionSave.getSubjectId());
        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());

    }

    @DisplayName("Test save a Session in the data base when endDateTime is null")
    @Test
    void testSaveASessionInTheDataBaseWhenEndDateTimeIsNull() throws Exception {

        var aSubject = getASubjectSaved();
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .subjectId(aSubject.getId())
                .build();

        var aSessionSave = sessionService.init(sessionCreateRequestDTO);

        Assertions.assertNotNull(aSessionSave.getStartDateTime());
        Assertions.assertNotNull(aSessionSave.getEndDateTime());
        Assertions.assertEquals(aSessionSave.getEndDateTime(),
                aSessionSave.getStartDateTime().plusMinutes(1));
        Assertions.assertNotNull(aSessionSave.getId());
        Assertions.assertNull(aSessionSave.getSubjectId());
        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());

    }

    @DisplayName("Test save a Session in the data base when the session of subject has already been started")
    @Test
    void testSaveASessionInTheDataBaseWhenTheSubjectHaveSession() throws Exception {
        var aSubject = getASubjectSaved();
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .subjectId(aSubject.getId())
                .build();

        var aSessionSave = sessionService.init(sessionCreateRequestDTO);

        var anohterSessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .subjectId(aSubject.getId())
                .build();

        DataIntegrityViolationException thrown = Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> sessionService.init(anohterSessionCreateRequestDTO)
        );

        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());
    }

    @DisplayName("Test save a Session in the data base when subjectId is null")
    @Test
    void testSaveASessionInTheDataBaseWhenSubjectIdIsNull() throws Exception {
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .build();

        InvalidDataAccessApiUsageException thrown = Assertions.assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> sessionService.init(sessionCreateRequestDTO)
        );

        Assertions.assertEquals(thrown.getMessage(), "The given id must not be null!; " +
                "nested exception is java.lang.IllegalArgumentException: " +
                "The given id must not be null!");


    }

    @DisplayName("Test save a Session in the data base when is a invalid subjectId")
    @Test
    void testSaveASessionInTheDataBaseWhenSessionIsBlank() throws Exception {
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .subjectId(-2L)
                .build();
        EntityNotFoundException thrown = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> sessionService.init(sessionCreateRequestDTO)
        );
        Assertions.assertEquals(thrown.getMessage(), "The Subject does not exist!");
    }

    @Test
    @DisplayName("Test find all")
    public void testFindAll() {
        PageRequest page = PageRequest.of(0, 1);
        Page<SessionDTO> sessions = sessionService.findAll(page);
        assertThat(sessions.get().count()).isLessThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Test find one")
    public void testFindOne() {


        var aSubject = getASubjectSaved();
        var currentDate = LocalDateTime.now();
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .endDateTime(currentDate.plusMinutes(60))
                .subjectId(aSubject.getId())
                .build();

        var aSessionSave = sessionService.init(sessionCreateRequestDTO);

        var anotherSession = sessionService.findOne(aSessionSave.getId()).get();

        Assertions.assertNotNull(anotherSession);
        Assertions.assertEquals(anotherSession.getId(), aSessionSave.getId());
        Assertions.assertEquals(anotherSession.getSubjectId(), aSessionSave.getSubjectId());
        Assertions.assertEquals(anotherSession.getStartDateTime(), aSessionSave.getStartDateTime());
        Assertions.assertEquals(anotherSession.getEndDateTime(), aSessionSave.getEndDateTime());
        Assertions.assertNull(aSessionSave.getSubjectId());
        Assertions.assertNull(anotherSession.getSubjectId());
        Assertions.assertEquals(anotherSession.getSubjectId(), aSubject.getId());
        sessionRepository.deleteById(aSessionSave.getId());
        subjectRepository.deleteById(aSubject.getId());
    }

    private SubjectDTO getASubjectSaved() {
        var subjectCreateRequestDTO = SubjectCreateRequestDTO.builder()
                .subject("One subject").build();
        return subjectService.save(subjectCreateRequestDTO);
    }
}
