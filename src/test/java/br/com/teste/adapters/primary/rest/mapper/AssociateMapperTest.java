package br.com.teste.adapters.primary.rest.mapper;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.primary.rest.dto.AssociateResponse;
import br.com.southsystem.adapters.primary.rest.mapper.AssociateMapper;
import br.com.southsystem.application.domain.entity.Associate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AssociateMapperTest {
    private AssociateMapper associateMapper;

    @BeforeEach
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
        associateMapper = Mappers.getMapper(AssociateMapper.class);
    }

    @Test
    void shouldConvertAssociateEntityToAssociateDTO() {
        Associate associatePostMock = Fixture.from(Associate.class).gimme("valid");
        AssociateResponse dto = associateMapper.toResponse(associatePostMock);

        Assertions.assertEquals(associatePostMock.getId(), dto.getId());
        Assertions.assertEquals(associatePostMock.getName(), dto.getName());
        Assertions.assertEquals(associatePostMock.getCpf(), dto.getCpf());
        Assertions.assertNotNull(associatePostMock.toString());
        Assertions.assertNotNull(dto.toString());
    }
}
