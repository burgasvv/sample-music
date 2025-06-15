package org.burgas.producerservice.service;

import org.burgas.producerservice.dto.IdentityPrincipal;
import org.burgas.producerservice.dto.LabelRequest;
import org.burgas.producerservice.dto.LabelResponse;
import org.burgas.producerservice.entity.Label;
import org.burgas.producerservice.exception.IdentityNotAuthorizedException;
import org.burgas.producerservice.exception.ProducerNotFoundException;
import org.burgas.producerservice.exception.ProducerNotOwnerException;
import org.burgas.producerservice.handler.RestClientHandler;
import org.burgas.producerservice.mapper.LabelMapper;
import org.burgas.producerservice.repository.LabelRepository;
import org.burgas.producerservice.repository.ProducerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class LabelService {

    public static final String IDENTITY_NOT_AUTHORIZED = "Пользователь не авторизован и не аутентифицирован";
    public static final String PRODUCER_NOT_OWNER = "Продюсер не является владельцем лейбла";
    public static final String PRODUCER_NOT_FOUND = "Продюсер с идентификатором %s не найден";
    public static final String LABEL_DELETED = "Label с идентификатором %s успешно удален";

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;
    private final RestClientHandler restClientHandler;
    private final ProducerRepository producerRepository;

    public LabelService(
            LabelRepository labelRepository, LabelMapper labelMapper,
            RestClientHandler restClientHandler, ProducerRepository producerRepository
    ) {
        this.labelRepository = labelRepository;
        this.labelMapper = labelMapper;
        this.restClientHandler = restClientHandler;
        this.producerRepository = producerRepository;
    }

    public List<LabelResponse> findAll() {
        return labelRepository
                .findAll()
                .stream()
                .map(labelMapper::toLabelResponse)
                .toList();
    }

    public LabelResponse findById(Long labelId) {
        return labelRepository
                .findById(labelId)
                .map(labelMapper::toLabelResponse)
                .orElseGet(LabelResponse::new);
    }

    public LabelResponse findByTitle(String title) {
        return labelRepository
                .findLabelByTitle(title)
                .map(labelMapper::toLabelResponse)
                .orElseGet(LabelResponse::new);
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(LabelRequest labelRequest, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        return producerRepository
                .findProducerByIdentityId(requireNonNull(identityPrincipal).getId())
                .map(
                        producer -> {
                            if (
                                    identityPrincipal.getAuthenticated() &&
                                    identityPrincipal.getId().equals(producer.getIdentityId())
                            ) {
                                if (requireNonNull(producer.getId()).equals(labelRequest.bossId())) {
                                    Label label = labelMapper.toLabel(labelRequest);
                                    label.setBossId(producer.getId());
                                    LabelResponse labelResponse = labelMapper.toLabelResponse(labelRepository.save(label));

                                    producer.setLabelId(labelResponse.getId());
                                    producerRepository.save(producer);
                                    return labelResponse.getId();

                                } else {
                                    throw new ProducerNotOwnerException(PRODUCER_NOT_OWNER);
                                }

                            } else {
                                throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED);
                            }
                        }
                )
                .orElseThrow(() -> new ProducerNotFoundException(format(PRODUCER_NOT_FOUND, labelRequest.bossId())));
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long labelId, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        Label label = labelRepository.findById(labelId).orElse(null);
        return producerRepository
                .findById(requireNonNull(label).getBossId())
                .map(
                        producer -> {
                            if (
                                    requireNonNull(identityPrincipal).getAuthenticated() &&
                                    identityPrincipal.getId().equals(producer.getIdentityId())
                            ) {
                                labelRepository
                                        .deleteById(requireNonNull(label.getId()));

                                return format(LABEL_DELETED, label.getId());

                            } else {
                                throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED);
                            }
                        }
                )
                .orElseThrow(() -> new ProducerNotFoundException(PRODUCER_NOT_FOUND));
    }
}
