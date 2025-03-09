package org.burgas.identityserver.dto;

import java.time.LocalDate;

public record IdentityRequest(
        Long id,
        String username,
        String password,
        String email,
        Long imageId,
        LocalDate registered,
        Long authorityId,
        Long subscriptionId
) {
}
