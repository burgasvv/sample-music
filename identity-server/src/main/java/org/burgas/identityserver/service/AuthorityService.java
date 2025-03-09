package org.burgas.identityserver.service;

import org.burgas.identityserver.dto.AuthorityRequest;
import org.burgas.identityserver.dto.AuthorityResponse;
import org.burgas.identityserver.exception.AuthorityNotFoundException;
import org.burgas.identityserver.mapper.AuthorityMapper;
import org.burgas.identityserver.repository.AuthorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static org.burgas.identityserver.entity.AuthorityMessage.AUTHORITY_DELETED;
import static org.burgas.identityserver.entity.AuthorityMessage.AUTHORITY_NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    public AuthorityService(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    public List<AuthorityResponse> findAll() {
        return authorityRepository
                .findAll()
                .stream()
                .map(authorityMapper::toAuthorityResponse)
                .toList();
    }

    public AuthorityResponse findById(Long authorityId) {
        return authorityRepository
                .findById(authorityId)
                .map(authorityMapper::toAuthorityResponse)
                .orElseGet(AuthorityResponse::new);
    }

    public AuthorityResponse findByName(String name) {
        return authorityRepository
                .findAuthorityByName(name)
                .map(authorityMapper::toAuthorityResponse)
                .orElseGet(AuthorityResponse::new);
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(final AuthorityRequest authorityRequestMono) {
        return authorityMapper
                .toAuthorityResponse(authorityRepository.save(authorityMapper.toAuthority(authorityRequestMono)))
                .getId();
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long authorityId) {
        return authorityRepository.findById(authorityId)
                .map(
                        authority -> {
                            authorityRepository.deleteById(Objects.requireNonNull(authority.getId()));
                            return format(AUTHORITY_DELETED.getMessage(), authority.getId());
                        }
                )
                .orElseThrow(
                        () -> new AuthorityNotFoundException(
                                format(AUTHORITY_NOT_FOUND.getMessage(), authorityId)
                        )
                );
    }
}
