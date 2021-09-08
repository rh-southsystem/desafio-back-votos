package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.repository.AffiliatedRepository;
import br.com.southsystem.cooperative.service.dto.AffiliatedDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AffiliatedServiceImplTest {

    @Autowired
    private AffiliatedRepository affiliatedRepository;

    @Autowired
    private AffiliatedServiceImpl affiliatedService;


    @DisplayName("Test save a Affiliated in the data base")
    @Test
    void testSaveAAffiliatedInTheDataBase() throws Exception {


        var affiliatedDTO = AffiliatedDTO.builder()
                .cpf("26283451020")
                .build();

        var aAffiliatedSave = affiliatedService.save(affiliatedDTO);

        Assertions.assertNotNull(aAffiliatedSave.getCpf());
        Assertions.assertEquals(affiliatedDTO.getCpf(), aAffiliatedSave.getCpf());
        Assertions.assertNotNull(aAffiliatedSave.getId());

        affiliatedRepository.deleteById(aAffiliatedSave.getId());


    }

    @DisplayName("Test save a Affiliated in the data base when the cpf is null")
    @Test
    void testSaveAAffiliatedInTheDataBaseWhenTheCpfIsNull() throws Exception {
        var affiliatedDTO = AffiliatedDTO.builder()
                .build();


        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> affiliatedService.save(affiliatedDTO)
        );

        Assertions.assertEquals(thrown.getConstraintViolations().size(), 2);

    }

    @DisplayName("Test save a Affiliated in the data base when the cpf is blank")
    @Test
    void testSaveAAffiliatedInTheDataBaseWhenTheCpfIsBlank() throws Exception {
        var affiliatedDTO = AffiliatedDTO.builder()
                .cpf("")
                .build();

        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> affiliatedService.save(affiliatedDTO)
        );

        Assertions.assertEquals(thrown.getConstraintViolations().size(), 2);

    }

    @DisplayName("Test save a Affiliated in the data base when the cpf is less than 11 digits")
    @Test
    void testSaveAAffiliatedInTheDataBaseWhenTheCpfIsLessThanElevenDigits() throws Exception {
        var affiliatedDTO = AffiliatedDTO.builder()
                .cpf("00000")
                .build();

        ConstraintViolationException thrown = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> affiliatedService.save(affiliatedDTO)
        );

        Assertions.assertEquals(thrown.getConstraintViolations().size(), 1);
    }

    @Test
    @DisplayName("Test find one Affiliated by cpf")
    public void testFindOneByCpf() {
        var affiliatedDTO = AffiliatedDTO.builder()
                .cpf("26283451020")
                .build();

        var aAffiliatedSave = affiliatedService.save(affiliatedDTO);

        var anotherAffiliated = affiliatedService.findOneByCpf(aAffiliatedSave.getCpf()).get();

        Assertions.assertNotNull(anotherAffiliated);
        Assertions.assertEquals(anotherAffiliated.getId(),aAffiliatedSave.getId());
        Assertions.assertEquals(anotherAffiliated.getCpf(),aAffiliatedSave.getCpf());

        affiliatedRepository.deleteById(aAffiliatedSave.getId());

    }

    @Test
    @DisplayName("Test find one Affiliated by cpf or create if not exist")
    public void testFindOneByCpfOrCreateIfNotExist() {
        var affiliatedDTO = AffiliatedDTO.builder()
                .cpf("26283451020")
                .build();

        Assertions.assertFalse(affiliatedService.findOneByCpf(affiliatedDTO.getCpf()).isPresent());

        var aAffiliatedSave = affiliatedService.findOrCreateByCpf(affiliatedDTO.getCpf());

        Assertions.assertNotNull(aAffiliatedSave);
        Assertions.assertEquals(affiliatedDTO.getCpf(),aAffiliatedSave.getCpf());

        affiliatedRepository.deleteById(aAffiliatedSave.getId());

    }

}
