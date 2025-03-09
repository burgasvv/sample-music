package org.burgas.identityserver.mapper;

import org.burgas.identityserver.dto.IdentityRequest;
import org.burgas.identityserver.dto.IdentityResponse;
import org.burgas.identityserver.entity.Authority;
import org.burgas.identityserver.entity.Identity;
import org.burgas.identityserver.repository.AuthorityRepository;
import org.burgas.identityserver.repository.IdentityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public final class IdentityMapper {

    private final IdentityRepository identityRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;
    private final PasswordEncoder passwordEncoder;

    public IdentityMapper(
            IdentityRepository identityRepository,
            AuthorityRepository authorityRepository,
            AuthorityMapper authorityMapper, PasswordEncoder passwordEncoder
    ) {
        this.identityRepository = identityRepository;
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private <T> T getData(T first, T second) {
        return first == null || first == "" ? second : first;
    }

    public Identity toIdentity(IdentityRequest identityRequest) {
        Long identityId = getData(identityRequest.id(), 0L);
        return identityRepository
                .findById(identityId)
                .map(
                        identity -> {
                            Long authorityId = identityRequest.authorityId() == 2L ? 3L : null;
                            Identity build = Identity.builder()
                                    .id(identity.getId())
                                    .username(getData(identityRequest.username(), identity.getUsername()))
                                    .email(getData(identityRequest.email(), identity.getEmail()))
                                    .authorityId(getData(authorityId, identity.getAuthorityId()))
                                    .registered(getData(identityRequest.registered(), identity.getRegistered()))
                                    .imageId(getData(identityRequest.imageId(), identity.getImageId()))
                                    .subscriptionId(getData(identityRequest.subscriptionId(), identity.getSubscriptionId()))
                                    .build();
                            if (identityRequest.password() == null || identityRequest.password().isEmpty())
                                build.setPassword(identity.getPassword());
                            else
                                build.setPassword(passwordEncoder.encode(identityRequest.password()));

                            return build;
                        }
                )
                .orElseGet(
                        () -> Identity.builder()
                                .username(identityRequest.username())
                                .password(passwordEncoder.encode(identityRequest.password()))
                                .email(identityRequest.email())
                                .registered(LocalDate.now())
                                .authorityId(getData(identityRequest.authorityId() == 2L ? 3L : null, 3L))
                                .subscriptionId(identityRequest.subscriptionId())
                                .imageId(identityRequest.imageId())
                                .build()
                );
    }

    public IdentityResponse toIdentityResponse(Identity identity) {
        return IdentityResponse.builder()
                .id(identity.getId())
                .username(identity.getUsername())
                .password(identity.getPassword())
                .email(identity.getEmail())
                .imageId(identity.getImageId())
                .registered(
                        identity.getRegistered().format(
                                DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        )
                )
                .authority(
                        authorityMapper.toAuthorityResponse(
                                authorityRepository.findById(identity.getAuthorityId())
                                        .orElseGet(Authority::new)
                        )
                )
                .subscriptionId(identity.getSubscriptionId())
                .build();
    }
}
