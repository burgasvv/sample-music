package org.burgas.identityserver.service;

import org.burgas.identityserver.dto.IdentityPrincipal;
import org.burgas.identityserver.dto.IdentityRequest;
import org.burgas.identityserver.dto.IdentityResponse;
import org.burgas.identityserver.entity.Identity;
import org.burgas.identityserver.exception.IdentityNotAuthorizedException;
import org.burgas.identityserver.exception.IdentityNotFoundException;
import org.burgas.identityserver.handler.RestClientHandler;
import org.burgas.identityserver.mapper.IdentityMapper;
import org.burgas.identityserver.repository.IdentityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.burgas.identityserver.entity.IdentityMessage.IDENTITY_DELETED;
import static org.burgas.identityserver.entity.IdentityMessage.IDENTITY_NOT_AUTHORIZED;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final IdentityMapper identityMapper;
    private final RestClientHandler restClientHandler;

    public IdentityService(
            IdentityRepository identityRepository,
            IdentityMapper identityMapper, RestClientHandler restClientHandler
    ) {
        this.identityRepository = identityRepository;
        this.identityMapper = identityMapper;
        this.restClientHandler = restClientHandler;
    }

    public List<IdentityResponse> findAll() {
        return identityRepository
                .findAll()
                .stream()
                .map(identityMapper::toIdentityResponse)
                .toList();
    }

    public IdentityResponse findByProducerToken(UUID token) {
        return identityRepository
                .findIdentityByProducerToken(token)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(IdentityResponse::new);
    }

    public IdentityResponse findByPaymentToken(UUID token) {
        return identityRepository
                .findIdentityByPaymentToken(token)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(IdentityResponse::new);
    }

    public IdentityResponse findById(Long identityId, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        Identity identity = identityRepository.findById(identityId).orElse(null);
        if (
                requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(requireNonNull(identity).getId())
        ) {
            return identityMapper
                    .toIdentityResponse(identity);

        } else {
            throw new IdentityNotFoundException(IDENTITY_NOT_AUTHORIZED.getMessage());
        }
    }

    public IdentityResponse findByUsername(String username, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        Identity identity = identityRepository.findIdentityByUsername(username).orElse(null);
        if (
                requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(requireNonNull(identity).getId())
        ) {
            return identityMapper
                    .toIdentityResponse(identity);

        } else {
            throw new IdentityNotFoundException(IDENTITY_NOT_AUTHORIZED.getMessage());
        }
    }

    @Transactional(
            isolation = READ_COMMITTED, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(IdentityRequest identityRequest, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        if (
                !requireNonNull(identityPrincipal).getAuthenticated() && (
                        identityRequest.id() == null || identityRequest.id() <= 0L)
        ) {
            return createOrUpdateIdentity(identityRequest);

        } else if (identityPrincipal.getId().equals(identityRequest.id())) {
            return createOrUpdateIdentity(identityRequest);

        } else {
            throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED.getMessage());
        }
    }

    private Long createOrUpdateIdentity(IdentityRequest identityRequest) {
        return identityMapper
                .toIdentityResponse(identityRepository.save(identityMapper.toIdentity(identityRequest)))
                .getId();
    }

    @Transactional(
            isolation = READ_COMMITTED, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long identityId, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        Identity identity = identityRepository.findById(identityId).orElse(null);
        if (
                requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(requireNonNull(identity).getId())
        ) {
            identityRepository.deleteById(identity.getId());
            return format(IDENTITY_DELETED.getMessage(), identity.getId());

        } else {
            throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED.getMessage());
        }
    }
}
