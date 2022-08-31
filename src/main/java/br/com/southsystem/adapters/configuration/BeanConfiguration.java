package br.com.southsystem.adapters.configuration;

import br.com.southsystem.Application;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class BeanConfiguration {

    @Bean
    WebClient webClient() throws SSLException {
        final SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

         final HttpClient tpClient = HttpClient
                                        .create()
                                        .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        ClientHttpConnector connector = new ReactorClientHttpConnector(tpClient);

        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(connector)
                .build();
    }

}
