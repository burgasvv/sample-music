package org.burgas.identityserver.mapper;

import org.burgas.identityserver.dto.IdentityPrincipal;
import org.burgas.identityserver.dto.IdentityResponse;
import org.springframework.stereotype.Component;

@Component
public final class IdentityPrincipalMapper {

    public IdentityPrincipal toIdentityPrincipal(
            final IdentityResponse identityResponse, Boolean authenticated
    ) {
        return IdentityPrincipal.builder()
                .id(identityResponse.getId())
                .username(identityResponse.getUsername())
                .password(identityResponse.getPassword())
                .email(identityResponse.getEmail())
                .authority(identityResponse.getAuthority().getAuthority())
                .authenticated(authenticated)
                .build();
    }
}
