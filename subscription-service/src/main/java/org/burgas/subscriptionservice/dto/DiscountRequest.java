package org.burgas.subscriptionservice.dto;

import java.time.LocalDate;
import java.util.List;

public record DiscountRequest(
        Long id,
        String name,
        String description,
        Long percentage,
        LocalDate starts,
        LocalDate ends,
        Boolean actual,
        List<Long> planIds
) {
}
