package org.burgas.paymentservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.paymentservice.dto.IdentityResponse;
import org.burgas.paymentservice.dto.SubscriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "getIdentityResponseByPaymentToken", fallbackMethod = "fallBackGetIdentityResponseByPaymentToken")
    public ResponseEntity<IdentityResponse> getIdentityResponseByPaymentToken(UUID token) {
        return restClient
                .get()
                .uri("http://localhost:8765/identities/by-payment-token/{token}", token)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<IdentityResponse> fallBackGetIdentityResponseByPaymentToken(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @CircuitBreaker(name = "getSubscriptionResponseByIdentityId", fallbackMethod = "fallBackGetSubscriptionResponseByIdentityId")
    public ResponseEntity<SubscriptionResponse> getSubscriptionResponseByIdentityId(Long identityId, String authentication) {
        return restClient
                .get()
                .uri("http://localhost:8765/subscriptions/by-identity?identityId=" + identityId)
                .header(AUTHORIZATION, authentication)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(SubscriptionResponse.class);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<SubscriptionResponse> fallBackGetSubscriptionResponseByIdentityId(Throwable throwable) {
        return ResponseEntity.ok(SubscriptionResponse.builder().build());
    }
}
