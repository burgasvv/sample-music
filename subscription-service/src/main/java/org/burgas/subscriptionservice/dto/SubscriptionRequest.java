package org.burgas.subscriptionservice.dto;

public record SubscriptionRequest(
        Long id,
        Long planId,
        Long identityId,
        Boolean cancelled
) {
}
