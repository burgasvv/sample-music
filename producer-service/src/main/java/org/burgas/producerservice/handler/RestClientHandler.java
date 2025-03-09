package org.burgas.producerservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.producerservice.dto.IdentityPrincipal;
import org.burgas.producerservice.dto.IdentityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.UUID;

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
                .uri(URI.create("http://localhost:8765/authentication/principal"))
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityPrincipal> fallBackGetPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().authenticated(false).build());
    }

    @CircuitBreaker(name = "getIdentityResponseByProducerToken", fallbackMethod = "fallBackGetIdentityResponseByProducerToken")
    public ResponseEntity<IdentityResponse> getIdentityResponseByProducerToken(UUID token) {
        return restClient
                .get()
                .uri("http://localhost:8765/identities/by-producer-token/{token}", token)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityResponseByProducerToken(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }
}
