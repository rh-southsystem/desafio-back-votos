package br.com.teste.application.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.application.domain.entity.VoteSession;
import br.com.southsystem.application.exception.VoteSessionExistsException;
import br.com.southsystem.application.exception.VoteSessionNotFoundException;
import br.com.southsystem.application.port.secondary.ResultVoteSessionSecondaryRepositoryPort;
import br.com.southsystem.application.port.secondary.VoteSessionSecondaryRepositoryPort;
import br.com.southsystem.application.service.VoteSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoteSessionServiceTest {
    private final String VOTE_SESSION_DESC = "Vote Session 01";
    private final Long VOTE_SESSION_ID = 1L;

    @Mock
    private VoteSessionSecondaryRepositoryPort secondaryRepositoryPort;
    @Mock
    private ResultVoteSessionSecondaryRepositoryPort resultVoteSessionSecondaryRepositoryPort;
    private VoteSessionService voteSessionService;

    @BeforeEach
    public void setUp() {
        voteSessionService = new VoteSessionService(secondaryRepositoryPort, resultVoteSessionSecondaryRepositoryPort);
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
    }

    @Test
    void shouldVoteSessionById() {
        VoteSession voteSessionSalvo = Fixture.from(VoteSession.class).gimme("valid");
        when(secondaryRepositoryPort.findById(VOTE_SESSION_ID)).thenReturn(Mono.just(voteSessionSalvo));

        StepVerifier.create(voteSessionService.findById(VOTE_SESSION_ID))
                .assertNext( voteSession -> {
                    assertNotNull(voteSession);
                    assertEquals(voteSessionSalvo.getId(), voteSession.getId());
                    assertEquals(voteSessionSalvo.getDescription(), voteSession.getDescription());
                    assertEquals(voteSessionSalvo.getStartDateTime(), voteSession.getStartDateTime());
                    assertEquals(voteSessionSalvo.getEndDateTime(), voteSession.getEndDateTime());
                })
                .verifyComplete();
    }

    @Test
    void shouldVoteSessionById_ReturnVoteSessionNotFoundExpetion(){
        Long voteSessionId = Long.MAX_VALUE;
        when(secondaryRepositoryPort.findById(voteSessionId)).thenReturn(Mono.empty());
        StepVerifier.create(voteSessionService.findById(voteSessionId)).expectError(VoteSessionNotFoundException.class).verify();
    }

    @Test
    void shouldVerifyDuplicateByDescriptionAndToSave() {
        VoteSession voteSessionComId = Fixture.from(VoteSession.class).gimme("valid");
        VoteSession voteSessionSemId = VoteSession.of(null,VOTE_SESSION_DESC, true,
                LocalDateTime.of(2022,10,1,00,00),
                LocalDateTime.of(2022,10,5,23,59),
                null);

        when(secondaryRepositoryPort.save(voteSessionSemId)).thenReturn(Mono.just(voteSessionComId));
        when(secondaryRepositoryPort.existsByDescription(VOTE_SESSION_DESC)).thenReturn(Mono.just(Boolean.FALSE));

        StepVerifier.create(voteSessionService.saveVoteSession(voteSessionSemId))
                .assertNext( voteSession -> {
                    assertNotNull(voteSession);
                    assertNotNull(voteSession.getId());
                    assertEquals(voteSessionComId.getDescription(), voteSession.getDescription());
                    assertEquals(voteSessionComId.getStartDateTime(), voteSession.getStartDateTime());
                    assertEquals(voteSessionComId.getEndDateTime(), voteSession.getEndDateTime());
                })
                .verifyComplete();
    }

    @Test
    void shouldVerifyDuplicateByDescriptionAndToSave_ReturnVoteSessionExistsException() {
        VoteSession voteSessionComId = Fixture.from(VoteSession.class).gimme("valid_2");
        VoteSession voteSessionSemId = VoteSession.of(null,VOTE_SESSION_DESC, true,
                LocalDateTime.of(2022,10,1,00,00),
                LocalDateTime.of(2022,10,5,23,59),
                null);

        when(secondaryRepositoryPort.existsByDescription(anyString())).thenReturn(Mono.just(Boolean.TRUE));

        StepVerifier.create(voteSessionService.saveVoteSession(voteSessionSemId))
                .expectError(VoteSessionExistsException.class)
                .verify();

    }
}
