package br.com.southsystem.adapters.secondary.api;

import br.com.southsystem.adapters.secondary.api.entity.APIResponse;
import br.com.southsystem.application.exception.CpfInvalidException;
import br.com.southsystem.application.port.secondary.AssociateSecondaryApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserInfoApiExternal implements AssociateSecondaryApiPort {

    private final WebClient webClient;

    @Value("${sistema-externo.userinfo.protocolo}")
    private String apiProtocolo;

    @Value("${sistema-externo.userinfo.host}")
    private String apiHost;

    @Value("${sistema-externo.userinfo.url-validar.path}")
    private String apiPath;

    @Override
    public Mono<Boolean> verifyStatusVoteAssociateByCpf(String associateCpf) {
            log.info("call method verifyStatusVoteAssociateByCpf by cpf: {} ",  associateCpf);
            return webClient.get().uri(uriBuilder -> uriBuilder.scheme(apiProtocolo).host(apiHost).path(apiPath).build(associateCpf))
                .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> response.bodyToMono(String.class).map(b -> new CpfInvalidException()))
                .bodyToMono(APIResponse.class)
                .map(response -> response.getStatus().equalsIgnoreCase(APIResponse.ABLE_TO_VOTE) ? Boolean.TRUE : Boolean.FALSE);
    }
}
