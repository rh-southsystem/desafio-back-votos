package br.com.teste.adapters.secondary.postgresql;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.secondary.postgresql.entity.VotingPost;
import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.VotingRepository;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.VotingDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VotingRepositoryTest {

    @Mock
    private VotingDataRepository votingDataRepository;
    private VotingRepository votingRepository;

    private PostgreSqlMapper  sqlMapper = Mappers.getMapper(PostgreSqlMapper.class);

    @BeforeEach
    public void setUp() {
        votingRepository = new VotingRepository(votingDataRepository, sqlMapper);
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
    }

    @Test
    void shouldFindVotingPostById() {
        Long votingId = 1L;

        VotingPost votingPostMock = Fixture.from(VotingPost.class).gimme("valid");
        when(votingDataRepository.findById(votingId)).thenReturn(Mono.just(votingPostMock));

        VotingPost votingPost = votingDataRepository.findById(votingId).block();

        Assertions.assertEquals(votingId, votingPost.getId());
        Assertions.assertEquals(votingId, votingPostMock.getId());
        Assertions.assertEquals(votingPost.getVoteSessionId(), votingPostMock.getVoteSessionId());
        Assertions.assertEquals(votingPost.getAssociateId(), votingPostMock.getAssociateId());
        Assertions.assertEquals(votingPost.getVoteType(), votingPostMock.getVoteType());
    }

    @Test
    void shouldToSaveVoting() {
        VotingPost votingPostMock = Fixture.from(VotingPost.class).gimme("valid");

        VotingPost votingPostSave = VotingPost.of(null, votingPostMock.getVoteSessionId(),votingPostMock.getAssociateId(), votingPostMock.getVoteType());

        when(votingDataRepository.save(any())).thenReturn(Mono.just(votingPostMock));

        VotingPost votingPost = votingDataRepository.save(votingPostSave).block();

        Assertions.assertEquals(votingPost.getVoteSessionId(), votingPostMock.getVoteSessionId());
        Assertions.assertEquals(votingPost.getAssociateId(), votingPostMock.getAssociateId());
        Assertions.assertEquals(votingPost.getVoteType(), votingPostMock.getVoteType());
    }

    @Test
    void shouldVerifyExistsByVoteSessionIdAndAssociateId_ReturnTrue(){
        when(votingDataRepository.existsByVoteSessionIdAndAssociateId(anyLong(), anyLong())).thenReturn(Mono.just(Boolean.TRUE));
        assertEquals(Boolean.TRUE, votingDataRepository.existsByVoteSessionIdAndAssociateId(1l, 1l).block());
    }

    @Test
    void shouldVerifyExistsByVoteSessionIdAndAssociateId_ReturnFalse(){
        when(votingDataRepository.existsByVoteSessionIdAndAssociateId(anyLong(), anyLong())).thenReturn(Mono.just(Boolean.FALSE));
        assertEquals(Boolean.FALSE, votingDataRepository.existsByVoteSessionIdAndAssociateId(1l, 2l).block());
    }

}
