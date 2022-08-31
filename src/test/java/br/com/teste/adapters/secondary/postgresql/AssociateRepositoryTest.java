package br.com.teste.adapters.secondary.postgresql;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.secondary.postgresql.entity.AssociatePost;
import br.com.southsystem.adapters.secondary.postgresql.mapper.PostgreSqlMapper;
import br.com.southsystem.adapters.secondary.postgresql.repository.AssociateRepository;
import br.com.southsystem.adapters.secondary.postgresql.repository.data.AssociateDataRepository;
import br.com.southsystem.application.domain.entity.Associate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssociateRepositoryTest {

    @Mock
    private AssociateDataRepository associateDataRepository;
    private AssociateRepository associadoRepository;

    private final PostgreSqlMapper  sqlMapper = Mappers.getMapper(PostgreSqlMapper.class);

    @BeforeEach
    public void setUp() {
        associadoRepository = new AssociateRepository(associateDataRepository, sqlMapper);
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
    }

    @Test
    void shouldFindAssociatePostById() {
        String cpfEsperado = "05340638103";

        AssociatePost associatePostMock = Fixture.from(AssociatePost.class).gimme("valid_2");
        when(associateDataRepository.findByCpf(cpfEsperado)).thenReturn(Mono.just(associatePostMock));

        Associate associate = associadoRepository.findByCpf(cpfEsperado).block();

        Assertions.assertEquals(cpfEsperado, associatePostMock.getCpf());
        Assertions.assertEquals(associatePostMock.getId(), associate.getId());
        Assertions.assertEquals(associatePostMock.getName(), associate.getName());
        Assertions.assertEquals(associatePostMock.getCpf(), associate.getCpf());
    }

}
