package br.com.teste.adapters.primary.rest.controller;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.Application;
import br.com.southsystem.adapters.primary.rest.mapper.AssociateMapper;
import br.com.southsystem.application.port.primary.AssociatePrimaryPort;
import br.com.teste.config.DatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import({DatabaseConfig.class, JacksonAutoConfiguration.class})
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssociateControllerTest {

    @Mock
    private AssociatePrimaryPort associatePrimaryPort;

    @Mock
    private AssociateMapper mapper;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
    }

    @Test
    void shouldGetAssociateByCpf_ReturnHttp200(){
            webClient
                .get().uri("/associates/v1.0/{cpfAssociate}", "96722007146")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Emanuel Hugo Augusto Aragão")
                .jsonPath("$.cpf").isEqualTo("96722007146");
    }
}
