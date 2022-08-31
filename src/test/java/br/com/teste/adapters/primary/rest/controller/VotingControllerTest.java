package br.com.teste.adapters.primary.rest.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.Application;
import br.com.southsystem.adapters.primary.rest.dto.VotingRequest;
import br.com.southsystem.adapters.primary.rest.mapper.VotingMapper;
import br.com.southsystem.application.port.primary.VotingPrimaryPort;
import br.com.teste.config.DatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.mockito.Mockito.mock;

@AutoConfigureWireMock(port = 80)
@Import({DatabaseConfig.class, JacksonAutoConfiguration.class})
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VotingControllerTest {

    private VotingPrimaryPort votingPrimaryPort;
    private VotingMapper mapper;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
        mapper = Mappers.getMapper(VotingMapper.class);
        votingPrimaryPort = mock(VotingPrimaryPort.class);
    }

    @Test
    void shouldGetVotingById_ReturnHttp200(){
            webClient
                .get().uri("/voting/v1.0/{votingId}", "1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.associate").isNotEmpty()
                .jsonPath("$.voteSession").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.associate.id").isEqualTo(1L)
                .jsonPath("$.voteSession.id").isEqualTo(1L);
    }

    @Test
    void shouldSaveVoting_ReturnHttp400_voteSessionNull(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("valid");
        requestDTO.setVoteSessionId(null);
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("The Vote Session cannot be null");
    }

    @Test
    void shouldSaveVoting_ReturnHttp400_associateNull(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("valid");
        requestDTO.setAssociateId(null);
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("The Associate cannot be null");
    }

    @Test
    void shouldSaveVoting_ReturnHttp400_voteTypeInvalid(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("valid");
        requestDTO.setVoteType("YYYYYYYYYYYYYYYYYYYYYYYYYY");
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("must be any of enum \"YES|NO\"");
    }

    @Test
    void shouldSaveVoting_ReturnHttp400_voteSessionNotFound(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("valid");
        requestDTO.setVoteSessionId(Long.MAX_VALUE);
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("Vote Session not found");
    }



    @Test
    void shouldSaveVoting_ReturnHttp400_associateNotFound(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("valid");
        requestDTO.setAssociateId(Long.MAX_VALUE);
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("Associate not found");
    }

    @Test
    void shouldSaveVoting_ReturnHttp200(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("valid");
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.voteSession").isNotEmpty()
                .jsonPath("$.voteSession.id").isNotEmpty()
                .jsonPath("$.associate").isNotEmpty()
                .jsonPath("$.associate.id").isNotEmpty();
    }

    @Test
    void shouldSaveVoting_ReturnHttp400_voteRegistered(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("invalid");
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("This vote was previously registered");
    }

    @Test
    void shouldSaveVoting_ReturnHttp400_dateExpired(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("dateExpired");
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("The date of session vote expired");
    }

    @Test
    void shouldSaveVoting_ReturnHttp400_unableVote(){
        VotingRequest requestDTO = Fixture.from(VotingRequest.class).gimme("unable_vote");
        webClient
                .post().uri("/voting/v1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.message").isEqualTo("Associate cannot vote");
    }

}
