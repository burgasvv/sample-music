package org.burgas.imageservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.imageservice.dto.IdentityPrincipal;
import org.burgas.imageservice.dto.IdentityResponse;
import org.burgas.imageservice.dto.LabelResponse;
import org.burgas.imageservice.dto.ProducerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "getIdentityPrincipal", fallbackMethod = "fallBackGetIdentityPrincipal")
    public ResponseEntity<IdentityPrincipal> getPrincipal(String authentication) {
        return restClient
                .get()
                .uri("http://localhost:8765/authentication/principal")
                .header(AUTHORIZATION, authentication)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityPrincipal> fallBackGetIdentityPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().authenticated(false).build());
    }

    @CircuitBreaker(name = "getIdentityById", fallbackMethod = "fallBackGetIdentityById")
    public ResponseEntity<IdentityResponse> getIdentityById(Long identityId, String authentication) {
        return restClient
                .get()
                .uri("http://localhost:8765/identities/by-id?identityId=" + identityId)
                .header(AUTHORIZATION, authentication)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityById(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @CircuitBreaker(name = "getProducerByIdentityId", fallbackMethod = "fallBackGetProducerByIdentityId")
    public ProducerResponse getProducerByIdentityId(final Long identityId, final String authentication) {
        return restClient
                .get()
                .uri("http://localhost:9000/producers/by-identity?identityId=" + identityId)
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(ProducerResponse.class)
                .getBody();
    }

    @SuppressWarnings("unused")
    private ResponseEntity<ProducerResponse> fallBackGetProducerByIdentityId(Throwable throwable) {
        return ResponseEntity.ok(ProducerResponse.builder().build());
    }

    @CircuitBreaker(name = "getLabelById", fallbackMethod = "fallBackGetLabelById")
    public ResponseEntity<LabelResponse> getLabelById(Long labelId, String authentication) {
        return restClient
                .get()
                .uri("http://localhost:9000/labels/by-id?labelId=" + labelId)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(LabelResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<LabelResponse> fallBackGetLabelById(Throwable throwable) {
        return ResponseEntity.ok(LabelResponse.builder().build());
    }
}
