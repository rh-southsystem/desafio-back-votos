package br.com.teste.adapters.secondary.postgresql;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.VoteSessionDataRepository;
import br.com.southsystem.adapters.secondary.postgresql.repository.VoteSessionRepository;
import br.com.southsystem.adapters.secondary.postgresql.entity.VoteSessionPost;
import br.com.southsystem.application.domain.entity.VoteSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoteSessionRepositoryTest {

    @Mock
    private VoteSessionDataRepository voteSessionDataRepository;

    private VoteSessionRepository voteSessionRepository;
    private final PostgreSqlMapper sqlMapper = Mappers.getMapper(PostgreSqlMapper.class);


    private final String VOTE_SESSION_DESC = "Vote Session 01";
    private final Long VOTE_SESSION_ID = 1L;

    @BeforeEach
    public void setUp() {
        voteSessionRepository = new VoteSessionRepository(voteSessionDataRepository, sqlMapper);
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
    }

    @Test
    void shouldFindVoteSessionById() {
        VoteSessionPost voteSessionSalvo = Fixture.from(VoteSessionPost.class).gimme("valid");
        when(voteSessionDataRepository.findById(anyLong())).thenReturn(Mono.just(voteSessionSalvo));

        VoteSession voteSession = voteSessionRepository.findById(VOTE_SESSION_ID).block();

        assertNotNull(voteSession);
        assertEquals(voteSessionSalvo.getId(), voteSession.getId());
        assertEquals(voteSessionSalvo.getDescription(), voteSession.getDescription());
        assertEquals(voteSessionSalvo.getStartDateTime(), voteSession.getStartDateTime());
        assertEquals(voteSessionSalvo.getEndDateTime(), voteSession.getEndDateTime());
    }

    @Test
    void shouldToSaveVoteSession() {
        VoteSessionPost voteSessionComId = Fixture.from(VoteSessionPost.class).gimme("valid");
        VoteSession voteSessionSemId = VoteSession.of(null,"Vote Session 01", true,
                                LocalDateTime.of(2022,10,1,00,00),
                                LocalDateTime.of(2022,10,5,23,59),
                                null);

        when(voteSessionDataRepository.save(any(VoteSessionPost.class))).thenReturn(Mono.just(voteSessionComId));

        VoteSession voteSessionSalvo = voteSessionRepository.save(voteSessionSemId).block();

        assertNotNull(voteSessionSalvo);
        assertNotNull(voteSessionSalvo.getId());
        assertEquals(voteSessionSalvo.getDescription(), voteSessionSemId.getDescription());
        assertEquals(voteSessionSalvo.getStartDateTime(), voteSessionSemId.getStartDateTime());
        assertEquals(voteSessionSalvo.getEndDateTime(), voteSessionSemId.getEndDateTime());
    }

    @Test
    void shouldVerifyExistsVoteSessionByDescription_ReturnTrue(){
        when(voteSessionDataRepository.existsByDescription(VOTE_SESSION_DESC)).thenReturn(Mono.just(Boolean.TRUE));
        assertEquals(Boolean.TRUE, voteSessionRepository.existsByDescription(VOTE_SESSION_DESC).block());
    }

    @Test
    void shouldVerifyExistsVoteSessionByDescription_ReturnFalse(){
        when(voteSessionDataRepository.existsByDescription(anyString())).thenReturn(Mono.just(Boolean.FALSE));
        assertEquals(Boolean.FALSE, voteSessionRepository.existsByDescription("Vote Session 0010").block());
    }

}
