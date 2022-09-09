package br.com.teste.config;

import br.com.southsystem.adapters.secondary.api.UserInfoApiExternal;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class ConfigTest {
    @Bean
    public MockWebServer webServer() {
        return new MockWebServer();
    }

    @Bean
    public WebClient webClient(MockWebServer webServer) {
        return WebClient.builder().baseUrl(webServer.url("").toString()).build();
    }

    @Bean
    public UserInfoApiExternal userInfoApiExternal(WebClient webClient) {
        return new UserInfoApiExternal(webClient);
    }

}
