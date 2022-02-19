package com.southsystem.votos.repository;

import com.southsystem.votos.entity.Agenda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class AgendaRepositoryTest {

    @Autowired
    AgendaRepository agendaRepository;

    @Test
    void findByActive_activeIsTrue_returnList() {
        List<Agenda> list = Arrays.asList(Agenda.builder().active(true).build(),
                Agenda.builder().active(true).build(),
                Agenda.builder().active(false).build());

        agendaRepository.saveAll(list);

        List<Agenda> listDb = agendaRepository.findByActive(true);

        Assertions.assertEquals(2, listDb.size());
    }

    @Test
    void findByActive_activeIsFalse_returnList() {
        List<Agenda> list = Arrays.asList(Agenda.builder().active(true).build(),
                Agenda.builder().active(true).build(),
                Agenda.builder().active(false).build());

        agendaRepository.saveAll(list);

        List<Agenda> listDb = agendaRepository.findByActive(false);

        Assertions.assertEquals(1, listDb.size());
    }

}
