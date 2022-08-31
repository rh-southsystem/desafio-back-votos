package br.com.teste.adapters.primary.rest.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.Application;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;
import br.com.southsystem.adapters.primary.rest.mapper.VoteSessionMapper;
import br.com.southsystem.application.port.primary.VoteSessionPrimaryPort;
import br.com.teste.config.DatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@Import({DatabaseConfig.class, JacksonAutoConfiguration.class})
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoteSessionControllerTest {

    @Mock
    private VoteSessionPrimaryPort voteSessionPrimaryPort;

    @InjectMocks
    private VoteSessionMapper mapper = Mappers.getMapper(VoteSessionMapper.class);

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
    }


    @Test
    void shouldGetFindVoteSessionsById_ReturnHttp200(){
            webClient
                .get().uri("/votesessions/v1.0/{voteSessionId}", "1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.description").isEqualTo("Vote Session 01");
    }

    @Test
    void shouldSaveVoteSession_ReturnHttp201(){
        VoteSessionRequest requestDTO = Fixture.from(VoteSessionRequest.class).gimme("valid");
        webClient
                .post().uri("/votesessions/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.description").isEqualTo(requestDTO.getDescription());
    }

    @Test
    void shouldSaveVoteSession_ReturnHttp400(){
        VoteSessionRequest requestDTO = Fixture.from(VoteSessionRequest.class).gimme("valid");
        requestDTO.setDescription(null);

        webClient
                .post().uri("/votesessions/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void shouldGetFindVoteSessionsWithResultsById_ReturnHttp200(){
        Long voteSessionId = 3L;
        webClient
                .get().uri("/votesessions/v1.0/{voteSessionId}/result", voteSessionId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo(voteSessionId)
                .jsonPath("$.result").isNotEmpty()
                .jsonPath("$.result.scoreAgainst").isEqualTo(3)
                .jsonPath("$.result.scoreFor").isEqualTo(1)
                .jsonPath("$.result.scoreTotal").isEqualTo(4);
    }

    @Test
    void shouldGetFindVoteSessionsWithResultsById_ReturnHttp400(){
        Long voteSessionId = 1L;
        webClient
                .get().uri("/votesessions/v1.0/{voteSessionId}/result", voteSessionId)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("Vote Session in progress");
    }
}
