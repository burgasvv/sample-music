package org.burgas.subscriptionservice.dto;

public record PaymentRequest(
        Long id,
        Long identityId,
        Long subscriptionId,
        Long price
) {
}
