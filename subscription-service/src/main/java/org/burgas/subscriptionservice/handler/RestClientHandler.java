package org.burgas.subscriptionservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.subscriptionservice.dto.IdentityPrincipal;
import org.burgas.subscriptionservice.dto.ProducerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static java.net.URI.create;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "getPrincipal", fallbackMethod = "fallBackGetPrincipal")
    public ResponseEntity<IdentityPrincipal> getPrincipal(String authentication) {
        return restClient
                .get()
                .uri(create("http://localhost:8765/authentication/principal"))
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityPrincipal> fallBackGetPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().authenticated(false).build());
    }

    @CircuitBreaker(name = "getProducerByIdentityId", fallbackMethod = "fallBackGetProducerByIdentityId")
    public ResponseEntity<ProducerResponse> getProducerByIdentityId(Long identityId, String authentication) {
        return restClient
                .get()
                .uri("http://localhost:8765/producers/by-identity?identityId=" + identityId)
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(ProducerResponse.class);
    }
}
