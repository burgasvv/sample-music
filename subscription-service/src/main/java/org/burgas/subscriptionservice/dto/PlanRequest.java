package org.burgas.subscriptionservice.dto;

public record PlanRequest(
        Long id,
        String name,
        Long price,
        String period,
        Long scores,
        String type,
        Long discountId
) {
}
