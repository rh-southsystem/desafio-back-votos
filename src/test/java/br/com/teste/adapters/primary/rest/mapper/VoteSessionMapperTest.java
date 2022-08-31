package br.com.teste.adapters.primary.rest.mapper;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResponse;
import br.com.southsystem.adapters.primary.rest.mapper.VoteSessionMapper;
import br.com.southsystem.application.domain.entity.VoteSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class VoteSessionMapperTest {
    private VoteSessionMapper voteSessionMapper;

    @BeforeEach
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
        voteSessionMapper = Mappers.getMapper(VoteSessionMapper.class);
    }

    @Test
    void shouldConvertVoteSessionEnityToVoteSessionDTO() {
        VoteSession voteSession = Fixture.from(VoteSession.class).gimme("valid_2");
        VoteSessionResponse voteSessionResponse = voteSessionMapper.toDTO(voteSession);

        assertNotNull(voteSessionResponse);
        assertEquals(voteSession.getId(), voteSessionResponse.getId());
        assertEquals(voteSession.getDescription(), voteSessionResponse.getDescription());
        assertEquals(voteSession.getStartDateTime(), voteSessionResponse.getStartDateTime());
        assertEquals(voteSession.getEndDateTime(), voteSessionResponse.getEndDateTime());
        assertEquals(Boolean.FALSE, voteSession.equals(voteSessionResponse));
        assertNotNull(voteSession.toString());
        assertNotNull(voteSessionResponse.toString());
    }

    @Test
    void shouldConvertVoteSessionDTOtoVoteSessionEntity() {
        VoteSessionRequest voteSessionRequest = Fixture.from(VoteSessionRequest.class).gimme("valid");
        VoteSession voteSessionEntity = voteSessionMapper.toEntity(voteSessionRequest);

        assertNotNull(voteSessionEntity);
        assertEquals(voteSessionRequest.getDescription(), voteSessionEntity.getDescription());
        assertEquals(voteSessionRequest.getStartDateTime(), voteSessionEntity.getStartDateTime());
        assertEquals(voteSessionRequest.getEndDateTime(), voteSessionEntity.getEndDateTime());
        assertEquals(Boolean.FALSE, voteSessionRequest.equals(voteSessionEntity));
        assertEquals(Boolean.FALSE, voteSessionEntity.equals(voteSessionRequest));
        assertNotNull(voteSessionRequest.toString());
        assertNotNull(voteSessionEntity.toString());
    }
}
