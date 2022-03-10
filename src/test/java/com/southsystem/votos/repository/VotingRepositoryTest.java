package com.southsystem.votos.repository;

import com.southsystem.votos.entity.Agenda;
import com.southsystem.votos.entity.Associate;
import com.southsystem.votos.entity.Voting;
import com.southsystem.votos.enums.VoteTypeEnum;
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
public class VotingRepositoryTest {

    @Autowired
    VotingRepository votingRepository;

    @Autowired
    AssociateRepository associateRepository;

    @Autowired
    AgendaRepository agendaRepository;

    @Test
    void countVotesByVoteTypeAndAgendaId_VoteTypeSim_AgendaId1_ReturnCount() {
        Associate associate = associateRepository.save(Associate.builder().cpf("23765260436").build());
        Agenda agenda = agendaRepository.save(Agenda.builder().description("Pauta 1").build());

        List<Voting> list = Arrays.asList(
                Voting.builder()
                        .agenda(agenda)
                        .associate(associate)
                        .voteType(VoteTypeEnum.SIM)
                        .build()
        );

        votingRepository.saveAll(list);

        Long countYes = votingRepository.countVotesByVoteTypeAndAgendaId(VoteTypeEnum.SIM.name(), agenda.getId());

        Assertions.assertEquals(1, countYes);
    }

}
