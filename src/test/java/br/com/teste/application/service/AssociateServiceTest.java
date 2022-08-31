package br.com.teste.application.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.application.domain.entity.Associate;
import br.com.southsystem.application.exception.AssociateNotFoundException;
import br.com.southsystem.application.port.secondary.AssociateSecondaryRepositoryPort;
import br.com.southsystem.application.service.AssociateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {

    private AssociateSecondaryRepositoryPort secondaryRepositoryPort;
    private AssociateService associadoService;

    @BeforeEach
    public void setUp() {
        secondaryRepositoryPort = mock(AssociateSecondaryRepositoryPort.class);
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
        associadoService = new AssociateService(secondaryRepositoryPort);
    }

    @Test
    void shouldFindAssociateByCpf(){
        String cpfEsperado = "96722007146";

        Associate associateMock = Fixture.from(Associate.class).gimme("valid");
        when(secondaryRepositoryPort.findByCpf(cpfEsperado)).thenReturn(Mono.just(associateMock));

        StepVerifier.create(associadoService.findByCpf(cpfEsperado))
                .assertNext( x -> {
                    assertNotNull(x);
                    assertEquals(cpfEsperado, associateMock.getCpf());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindAssociateByCpf_ReturnAssociateNotFoundExpetion(){
        String cpfEsperado = "96722007444";
        when(secondaryRepositoryPort.findByCpf(cpfEsperado)).thenReturn(Mono.empty());
        StepVerifier.create(associadoService.findByCpf(cpfEsperado)).expectError(AssociateNotFoundException.class).verify();
    }
}
