package org.burgas.producerservice.mapper;

import org.burgas.producerservice.dto.IdentityResponse;
import org.burgas.producerservice.dto.ProducerRequest;
import org.burgas.producerservice.dto.ProducerResponse;
import org.burgas.producerservice.entity.Producer;
import org.burgas.producerservice.handler.RestClientHandler;
import org.burgas.producerservice.repository.ProducerRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ProducerMapper {

    private final ProducerRepository producerRepository;
    private final RestClientHandler restClientHandler;

    public ProducerMapper(ProducerRepository producerRepository, RestClientHandler restClientHandler) {
        this.producerRepository = producerRepository;
        this.restClientHandler = restClientHandler;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Producer toProducerSave(final ProducerRequest producerRequest) {
        Long producerId = getData(producerRequest.id(), 0L);
        return producerRepository
                .findById(producerId)
                .map(
                        producer -> {
                            Producer saved = producerRepository.save(
                                    Producer.builder()
                                            .id(producer.getId())
                                            .nickname(getData(producerRequest.nickname(), producer.getNickname()))
                                            .firstname(getData(producerRequest.firstname(), producer.getFirstname()))
                                            .lastname(getData(producerRequest.lastname(), producer.getLastname()))
                                            .patronymic(getData(producerRequest.patronymic(), producer.getPatronymic()))
                                            .about(getData(producerRequest.about(), producer.getAbout()))
                                            .phone(getData(producerRequest.phone(), producer.getPhone()))
                                            .identityId(getData(producerRequest.identityId(), producer.getIdentityId()))
                                            .labelId(getData(producerRequest.labelId(), producer.getLabelId()))
                                            .bannerId(getData(producerRequest.bannerId(), producer.getBannerId()))
                                            .build()
                            );
                            producerRepository
                                    .updateIdentityBySettingAuthorityId(2L, producer.getIdentityId());
                            return saved;
                        }
                )
                .orElseGet(
                        () -> {
                            Producer producer = producerRepository.save(
                                    Producer.builder()
                                            .nickname(producerRequest.nickname())
                                            .firstname(producerRequest.firstname())
                                            .lastname(producerRequest.lastname())
                                            .patronymic(producerRequest.patronymic())
                                            .about(producerRequest.about())
                                            .phone(producerRequest.phone())
                                            .bannerId(producerRequest.bannerId())
                                            .labelId(producerRequest.labelId())
                                            .identityId(producerRequest.identityId())
                                            .build()
                            );
                            producerRepository.updateIdentityBySettingAuthorityId(2L, producer.getIdentityId());
                            producerRepository.createProducerIdentityToken(
                                            producer.getId(),
                                            UUID.randomUUID(),
                                            producer.getIdentityId()
                            );
                            return producer;
                        }
                );
    }

    public ProducerResponse toProducerResponse(final Producer producer) {
        UUID uuid = producerRepository
                .findTokenByProducerIdAndIdentityId(producer.getId(), producer.getIdentityId())
                .orElse(null);
        IdentityResponse identityResponse = restClientHandler.getIdentityResponseByProducerToken(uuid).getBody();
        return ProducerResponse.builder()
                .id(producer.getId())
                .nickname(producer.getNickname())
                .firstname(producer.getFirstname())
                .lastname(producer.getLastname())
                .patronymic(producer.getPatronymic())
                .phone("+7" + producer.getPhone())
                .about(producer.getAbout())
                .identity(identityResponse)
                .bannerId(producer.getBannerId())
                .labelId(producer.getLabelId())
                .build();
    }
}
