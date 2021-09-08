package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.repository.SubjectRepository;
import br.com.southsystem.cooperative.service.dto.SubjectCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.SubjectDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SubjectServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectServiceImpl subjectService;

    @DisplayName("Test save a Subject in the data base")
    @Test
    void testSaveASubjectInTheDataBase() throws Exception {
        var subjectCreateRequestDTO = SubjectCreateRequestDTO.builder()
                .subject("One subject").build();

        var aSubjectSave = subjectService.save(subjectCreateRequestDTO);

        Assertions.assertNotNull(aSubjectSave.getDateTime());
        Assertions.assertEquals(aSubjectSave.getSubject(), "One subject");
        Assertions.assertNotNull(aSubjectSave.getId());
        Assertions.assertNull(aSubjectSave.getSessionId());
        subjectRepository.deleteById(aSubjectSave.getId());

    }

    @DisplayName("Test save a Subject in the data base when subject is null")
    @Test
    void testSaveASubjectInTheDataBaseWhenSubjectIsNull() throws Exception {
        var subjectCreateRequestDTO = SubjectCreateRequestDTO.builder()
                .build();

        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> subjectService.save(subjectCreateRequestDTO)
        );

        Assertions.assertEquals(thrown.getConstraintViolations().size(), 2);


    }

    @DisplayName("Test save a Subject in the data base when subject is blank")
    @Test
    void testSaveASubjectInTheDataBaseWhenSubjectIsBlank() throws Exception {
        var subjectCreateRequestDTO = SubjectCreateRequestDTO.builder()
                .subject("")
                .build();

        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> subjectService.save(subjectCreateRequestDTO)
        );

        Assertions.assertEquals(thrown.getConstraintViolations().size(), 1);
    }

    @Test
    @DisplayName("Test find all")
    public void testFindAll() {
        PageRequest page = PageRequest.of(0, 1);
        Page<SubjectDTO> subjects = subjectService.findAll(page);
        assertThat(subjects.get().count()).isLessThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Test find one")
    public void testFindOne() {
        var subjectCreateRequestDTO = SubjectCreateRequestDTO.builder()
                .subject("One subject").build();

        var aSubjectSave = subjectService.save(subjectCreateRequestDTO);

        var anotherSubject = subjectService.findOne(aSubjectSave.getId()).get();

        Assertions.assertNotNull(anotherSubject);
        Assertions.assertEquals(anotherSubject.getId(), aSubjectSave.getId());
        Assertions.assertEquals(anotherSubject.getSubject(), aSubjectSave.getSubject());
        Assertions.assertEquals(anotherSubject.getDateTime(), aSubjectSave.getDateTime());
        Assertions.assertNull(aSubjectSave.getSessionId());
        Assertions.assertNull(anotherSubject.getSessionId());
        subjectRepository.deleteById(aSubjectSave.getId());

    }

}
