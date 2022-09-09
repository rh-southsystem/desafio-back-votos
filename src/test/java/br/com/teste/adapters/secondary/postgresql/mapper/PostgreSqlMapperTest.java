package br.com.teste.adapters.secondary.postgresql.mapper;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.secondary.postgresql.entity.AssociatePost;
import br.com.southsystem.adapters.secondary.postgresql.entity.VoteSessionPost;
import br.com.southsystem.adapters.secondary.postgresql.entity.VotingPost;
import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.application.domain.entity.Associate;
import br.com.southsystem.application.domain.entity.VoteSession;
import br.com.southsystem.application.domain.entity.Voting;
import br.com.southsystem.application.domain.entity.enums.VoteType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class PostgreSqlMapperTest {
    private PostgreSqlMapper postgreSqlMapper;

    @BeforeEach
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
        postgreSqlMapper = Mappers.getMapper(PostgreSqlMapper.class);
    }

    @Test
    void shouldConvertAssociatePostToAssociate() {
        AssociatePost associatePostMock = Fixture.from(AssociatePost.class).gimme("valid");
        Associate associate = postgreSqlMapper.toAssociateEntity(associatePostMock);

        Assertions.assertEquals(associatePostMock.getId(), associate.getId());
        Assertions.assertEquals(associatePostMock.getName(), associate.getName());
        Assertions.assertEquals(associatePostMock.getCpf(), associate.getCpf());
        Assertions.assertNotNull(associatePostMock.toString());
        Assertions.assertNotNull(associate.toString());
    }

    @Test
    void shouldConvertVoteSessionPostToVoteSession() {
        VoteSessionPost voteSessionPost = Fixture.from(VoteSessionPost.class).gimme("valid");
        VoteSession voteSession = postgreSqlMapper.toVoteSessionEntity(voteSessionPost);

        Assertions.assertEquals(voteSessionPost.getId(), voteSession.getId());
        Assertions.assertEquals(voteSessionPost.getDescription(), voteSession.getDescription());
        Assertions.assertEquals(voteSessionPost.getStartDateTime(), voteSession.getStartDateTime());
        Assertions.assertEquals(voteSessionPost.getEndDateTime(), voteSession.getEndDateTime());
        Assertions.assertEquals(voteSessionPost.getEnabled(), voteSession.getEnabled());
        Assertions.assertNotNull(voteSessionPost.toString());
        Assertions.assertNotNull(voteSession.toString());
    }

    @Test
    void shouldConvertVoteSessionToVoteSessionPost() {
        VoteSession voteSession = Fixture.from(VoteSession.class).gimme("valid");
        VoteSessionPost voteSessionPost = postgreSqlMapper.toVoteSessionPost(voteSession);

        Assertions.assertEquals(voteSession.getId(), voteSessionPost.getId());
        Assertions.assertEquals(voteSession.getDescription(), voteSessionPost.getDescription());
        Assertions.assertEquals(voteSession.getStartDateTime(), voteSessionPost.getStartDateTime());
        Assertions.assertEquals(voteSession.getEndDateTime(), voteSessionPost.getEndDateTime());
        Assertions.assertEquals(voteSession.getEnabled(), voteSessionPost.getEnabled());
        Assertions.assertNotNull(voteSessionPost.toString());
        Assertions.assertNotNull(voteSession.toString());
    }

    @Test
    void shouldConverterVotingPostToVoting() {
        VotingPost votingPost = Fixture.from(VotingPost.class).gimme("valid");
        Voting voting = postgreSqlMapper.toVotingEntity(votingPost);

        Assertions.assertEquals(1l, voting.getId());
        Assertions.assertEquals(1l, voting.getVoteSession().getId());
        Assertions.assertEquals(1l, voting.getAssociate().getId());
        Assertions.assertEquals(VoteType.NO, voting.getVoteType());
    }

    @Test
    void shouldConverterVotingToVotingPost() {
        Voting voting = Fixture.from(Voting.class).gimme("valid");
        VotingPost votingPost = postgreSqlMapper.toVotingPost(voting);

        Assertions.assertEquals(1l, votingPost.getId());
        Assertions.assertEquals(1l, votingPost.getVoteSessionId());
        Assertions.assertEquals(1l, votingPost.getAssociateId());
        Assertions.assertEquals(VoteType.NO.name(), votingPost.getVoteType().name());
    }
}
