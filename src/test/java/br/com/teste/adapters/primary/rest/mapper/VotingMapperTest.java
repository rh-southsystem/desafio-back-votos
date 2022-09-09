package br.com.teste.adapters.primary.rest.mapper;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.primary.rest.dto.VotingRequest;
import br.com.southsystem.adapters.primary.rest.dto.VotingResponse;
import br.com.southsystem.adapters.primary.rest.mapper.VotingMapper;
import br.com.southsystem.application.domain.entity.Voting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class VotingMapperTest {
    private VotingMapper votingMapper;

    @BeforeEach
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
        votingMapper = Mappers.getMapper(VotingMapper.class);
    }

    @Test
    void shouldConvertVotingRequestToVoting() {
        VotingRequest votingRequest = Fixture.from(VotingRequest.class).gimme("valid");
        Voting voting = votingMapper.toEntity(votingRequest);

        assertNotNull(voting);
        assertEquals(votingRequest.getVoteSessionId(), voting.getVoteSession().getId());
        assertEquals(votingRequest.getAssociateId(), voting.getAssociate().getId());
    }

    @Test
    void shouldConvertVotingToVotingResponse() {
        Voting voting = Fixture.from(Voting.class).gimme("valid");
        VotingResponse votingResponse = votingMapper.toResponse(voting);

        assertNotNull(votingResponse);
        assertEquals(voting.getId(), votingResponse.getId());
        assertEquals(voting.getVoteSession().getId(), votingResponse.getVoteSession().getId());
        assertEquals(voting.getAssociate().getId(), votingResponse.getAssociate().getId());
    }

}
