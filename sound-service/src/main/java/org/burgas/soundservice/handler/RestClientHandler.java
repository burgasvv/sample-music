package org.burgas.soundservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.soundservice.dto.IdentityPrincipal;
import org.burgas.soundservice.dto.LabelResponse;
import org.burgas.soundservice.dto.ProducerResponse;
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

    @CircuitBreaker(name = "getLabelById", fallbackMethod = "fallBackGetLabelById")
    public ResponseEntity<LabelResponse> getLabelById(Long labelId) {
        return restClient
                .get()
                .uri("http://localhost:9000/labels/by-id?labelId=" + labelId)
                .retrieve()
                .toEntity(LabelResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<LabelResponse> fallBackGetLabelById(Throwable throwable) {
        return ResponseEntity.ok(LabelResponse.builder().build());
    }

    @CircuitBreaker(name = "getPrincipal", fallbackMethod = "fallBackGetPrincipal")
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
    private ResponseEntity<IdentityPrincipal> fallBackGetPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().authenticated(false).build());
    }

    @CircuitBreaker(name = "getProducerByIdentityId", fallbackMethod = "fallBackGetProducerByIdentityId")
    public ResponseEntity<ProducerResponse> getProducerByIdentityId(final Long identityId, final String authentication) {
        return restClient
                .get()
                .uri("http://localhost:9000/producers/by-identity?identityId=" + identityId)
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(ProducerResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<ProducerResponse> fallBackGetProducerByIdentityId(final Long identityId, final String authentication) {
        return ResponseEntity.ok(ProducerResponse.builder().build());
    }
}
