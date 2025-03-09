package org.burgas.paymentservice.dto;

public record PaymentRequest(
        Long id,
        Long identityId,
        Long subscriptionId,
        Long price
) {
}
