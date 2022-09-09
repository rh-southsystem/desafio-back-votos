package br.com.teste.adapters.secondary.api;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.southsystem.adapters.secondary.api.UserInfoApiExternal;
import br.com.southsystem.adapters.secondary.api.entity.APIResponse;
import br.com.teste.config.ConfigTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;


@TestPropertySource(locations="classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@Import({ConfigTest.class, JacksonAutoConfiguration.class})
public class UserInfoApiExternalTest {
    @Autowired private ObjectMapper mapper;
    @Autowired
    private MockWebServer mockWebServer;

    @Autowired
    private UserInfoApiExternal infoApiExternal;

    @BeforeEach
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.teste.fixtures");
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                switch (request.getPath()) {
                    case "/users/96722007146":
                        return new MockResponse().setResponseCode(200).setBody(getJsonApiResponse("able"));
                    case "/users/96722007444":
                        return new MockResponse().setResponseCode(200).setBody(getJsonApiResponse("unable"));
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockWebServer.setDispatcher(dispatcher);
    }

    @Test
    void shouldVerifyStatusVoteByCpf_ReturnAbleToVote() {
        StepVerifier.create(infoApiExternal.verifyStatusVoteAssociateByCpf("96722007146")).equals(Boolean.TRUE);
    }

    @Test
    void shouldVerifyStatusVoteByCpf_ReturnUnableToVote() {
        StepVerifier.create(infoApiExternal.verifyStatusVoteAssociateByCpf("96722007444")).equals(Boolean.FALSE);
    }

    @Test
    void shouldVerifyStatusVoteByCpf_ReturnException() {
        StepVerifier.create(infoApiExternal.verifyStatusVoteAssociateByCpf("00000000000")).verifyComplete();
    }

    private String getJsonApiResponse(String fk_name) {
        APIResponse APIResponse = Fixture.from(APIResponse.class).gimme(fk_name);
        try {
            return mapper.writeValueAsString(APIResponse);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

}
