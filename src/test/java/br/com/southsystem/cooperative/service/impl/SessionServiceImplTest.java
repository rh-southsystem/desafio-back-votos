package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.repository.SessionRepository;

import br.com.southsystem.cooperative.service.dto.SessionDTO;
import br.com.southsystem.cooperative.service.dto.SessionInitRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
public class SessionServiceImplTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionServiceImpl sessionService;

    @DisplayName("Test save a Session in the data base")
    @Test
    void testSaveASessionInTheDataBase() throws Exception {
        var sessionCreateRequestDTO = SessionInitRequestDTO.builder()
                .("One session").build();

        var aSessionSave = sessionService.save(sessionCreateRequestDTO);

        Assertions.assertNotNull(aSessionSave.getDateTime());
        Assertions.assertEquals(aSessionSave.getSession(), "One session");
        Assertions.assertNotNull(aSessionSave.getId());
        Assertions.assertNull(aSessionSave.getSessionId());
        sessionRepository.deleteById(aSessionSave.getId());

    }

    @DisplayName("Test save a Session in the data base when session is null")
    @Test
    void testSaveASessionInTheDataBaseWhenSessionIsNull() throws Exception {
        var sessionCreateRequestDTO = SessionCreateRequestDTO.builder()
                .build();

        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> sessionService.save(sessionCreateRequestDTO)
        );

        Assertions.assertEquals(thrown.getConstraintViolations().size(),2);


    }
    @DisplayName("Test save a Session in the data base when session is blank")
    @Test
    void testSaveASessionInTheDataBaseWhenSessionIsBlank() throws Exception {
        var sessionCreateRequestDTO = SessionCreateRequestDTO.builder()
                .session("")
                .build();

        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> sessionService.save(sessionCreateRequestDTO)
        );

        Assertions.assertEquals(thrown.getConstraintViolations().size(),1);
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
        var sessionCreateRequestDTO = SessionCreateRequestDTO.builder()
                .session("One session").build();

        var aSessionSave = sessionService.save(sessionCreateRequestDTO);

        var anotherSession = sessionService.findOne(aSessionSave.getId()).get();

        Assertions.assertNotNull(anotherSession);
        Assertions.assertEquals(anotherSession.getId(),aSessionSave.getId());
        Assertions.assertEquals(anotherSession.getSession(),aSessionSave.getSession());
        Assertions.assertEquals(anotherSession.getDateTime(),aSessionSave.getDateTime());
        Assertions.assertNull(aSessionSave.getSessionId());
        Assertions.assertNull(anotherSession.getSessionId());
        sessionRepository.deleteById(aSessionSave.getId());

    }

}
