package org.burgas.producerservice.service;

import org.burgas.producerservice.dto.IdentityPrincipal;
import org.burgas.producerservice.dto.ProducerRequest;
import org.burgas.producerservice.dto.ProducerResponse;
import org.burgas.producerservice.entity.Producer;
import org.burgas.producerservice.exception.IdentityNotAuthorizedException;
import org.burgas.producerservice.handler.RestClientHandler;
import org.burgas.producerservice.mapper.ProducerMapper;
import org.burgas.producerservice.repository.ProducerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.burgas.producerservice.entity.ProducerMessage.PRODUCER_DELETED;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class ProducerService {

    public static final String IDENTITY_NOT_AUTHORIZED = "Пользователь не авторизован и не аутентифицирован";

    private final ProducerRepository producerRepository;
    private final ProducerMapper producerMapper;
    private final RestClientHandler restClientHandler;

    public ProducerService(
            ProducerRepository producerRepository,
            ProducerMapper producerMapper, RestClientHandler restClientHandler
    ) {
        this.producerRepository = producerRepository;
        this.producerMapper = producerMapper;
        this.restClientHandler = restClientHandler;
    }

    public List<ProducerResponse> findAll() {
        return producerRepository
                .findAll()
                .stream()
                .map(producerMapper::toProducerResponse)
                .toList();
    }

    public List<ProducerResponse> findProducersByLabelId(Long labelId) {
        return producerRepository
                .findProducersByLabelId(labelId)
                .stream()
                .map(producerMapper::toProducerResponse)
                .toList();
    }

    public ProducerResponse findById(Long producerId) {
        return producerRepository
                .findById(producerId)
                .map(producerMapper::toProducerResponse)
                .orElseGet(ProducerResponse::new);
    }

    public ProducerResponse findByIdentityId(Long producerId) {
        return producerRepository
                .findProducerByIdentityId(producerId)
                .map(producerMapper::toProducerResponse)
                .orElseGet(ProducerResponse::new);
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(final ProducerRequest producerRequest, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();

        if (requireNonNull(identityPrincipal).getAuthenticated() &&
            identityPrincipal.getId().equals(producerRequest.identityId())) {

            Producer producerSave = producerMapper.toProducerSave(producerRequest);
            return producerMapper.toProducerResponse(producerSave).getId();


        } else {
            throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED);
        }
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long producerId, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        Producer producer = producerRepository.findById(producerId).orElse(null);

        if (requireNonNull(identityPrincipal).getAuthenticated() &&
            identityPrincipal.getId().equals(requireNonNull(producer).getIdentityId())) {

            producerRepository.deleteById(requireNonNull(producer.getId()));
            return PRODUCER_DELETED.getMessage();

        } else {
            throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED);
        }
    }
}
