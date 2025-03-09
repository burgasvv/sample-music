package org.burgas.soundservice.service;

import org.burgas.soundservice.dto.IdentityPrincipal;
import org.burgas.soundservice.dto.SampleRequest;
import org.burgas.soundservice.dto.SampleResponse;
import org.burgas.soundservice.entity.IdentitySample;
import org.burgas.soundservice.entity.Pack;
import org.burgas.soundservice.entity.Sample;
import org.burgas.soundservice.exception.*;
import org.burgas.soundservice.handler.RestClientHandler;
import org.burgas.soundservice.mapper.SampleMapper;
import org.burgas.soundservice.repository.IdentitySampleRepository;
import org.burgas.soundservice.repository.PackRepository;
import org.burgas.soundservice.repository.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.burgas.soundservice.entity.PackMessage.*;
import static org.burgas.soundservice.entity.SampleMessage.*;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(readOnly = true, propagation = REQUIRED)
public class SampleService {

    private static final Logger log = LoggerFactory.getLogger(SampleService.class);
    private final SampleRepository sampleRepository;
    private final SampleMapper sampleMapper;
    private final PackRepository packRepository;
    private final RestClientHandler restClientHandler;
    private final IdentitySampleRepository identitySampleRepository;

    public SampleService(
            SampleRepository sampleRepository, SampleMapper sampleMapper, PackRepository packRepository,
            RestClientHandler restClientHandler, IdentitySampleRepository identitySampleRepository
    ) {
        this.sampleRepository = sampleRepository;
        this.sampleMapper = sampleMapper;
        this.packRepository = packRepository;
        this.restClientHandler = restClientHandler;
        this.identitySampleRepository = identitySampleRepository;
    }

    public List<SampleResponse> findByPackId(Long packId) {
        return sampleRepository
                .findSamplesByPackId(packId)
                .stream()
                .map(sampleMapper::toSampleResponse)
                .toList();
    }

    public SampleResponse findById(Long sampleId) {
        return sampleRepository
                .findById(sampleId)
                .map(sampleMapper::toSampleResponse)
                .orElseGet(SampleResponse::new);
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadSample(final MultipartFile multipartFile, final Long packId, final String authentication) {
        if (!multipartFile.isEmpty()) {

            if (packId != null) {
                try {
                    String format = requireNonNull(multipartFile.getContentType()).split("/")[1];
                    Sample build = Sample.builder()
                            .scores(1L)
                            .data(multipartFile.getBytes())
                            .contentType(multipartFile.getContentType())
                            .additions(0L)
                            .format(format)
                            .name(multipartFile.getOriginalFilename())
                            .size(multipartFile.getSize())
                            .packId(packId)
                            .build();
                    build = sampleRepository.save(build);

                    IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
                    if (identityPrincipal != null) {
                        identitySampleRepository.save(
                                IdentitySample.builder()
                                        .identityId(identityPrincipal.getId())
                                        .sampleId(build.getId())
                                        .build()
                        );
                        build.setAdditions(build.getAdditions() + 1);
                        sampleRepository.save(build);
                    } else {
                        log.warn("SAMPLE-SERVICE::Identity Not Authorized");
                        throw new IdentityNotAuthorizedException(NOT_AUTHORIZED.getMessage());
                    }
                    return format(SAMPLE_ADDED.getMessage(), build.getId());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                throw new PackNotFoundException(PACK_ID_NOT_SPECIFIED.getMessage());
            }

        } else {
            throw new EmptyFileException(EMPTY_FILE.getMessage());
        }
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String updateSample(final SampleRequest sampleRequest) {
        Long sampleId = getData(sampleRequest.id(), 0L);
        return sampleRepository
                .findById(sampleId)
                .map(
                        sample -> {
                            Sample build = Sample.builder()
                                    .id(sample.getId())
                                    .data(sample.getData())
                                    .scores(sample.getScores())
                                    .additions(sample.getAdditions())
                                    .bpm(getData(sampleRequest.bpm(), sample.getBpm()))
                                    .name(getData(sampleRequest.name(), sample.getName()))
                                    .keyId(getData(sampleRequest.keyId(), sample.getKeyId()))
                                    .packId(getData(sampleRequest.packId(), sample.getPackId()))
                                    .categoryId(getData(sampleRequest.categoryId(), sample.getCategoryId()))
                                    .data(sample.getData())
                                    .contentType(sample.getContentType())
                                    .format(sample.getFormat())
                                    .size(sample.getSize())
                                    .build();
                            sampleRepository.save(build);
                            return format(SAMPLE_UPDATED.getMessage(), sample.getId());
                        }
                )
                .orElseThrow(() -> new SampleNotFoundException(
                        format(SAMPLE_NOT_FOUND.getMessage(), sampleRequest.id()))
                );
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String addSample(final Long sampleId, final String authentication) {
        return sampleRepository
                .findById(sampleId)
                .map(
                        sample -> {

                            sample.setAdditions(sample.getAdditions() + 1);
                            if (sample.getAdditions() > 100 && sample.getAdditions() <= 500) {
                                sample.setScores(2L);
                            }
                            else if (sample.getAdditions() > 500 && sample.getAdditions() <= 1000) {
                                sample.setScores(3L);
                            }
                            else if (sample.getAdditions() > 1000) {
                                sample.setScores(5L);

                            } else if (sample.getAdditions() <= 100 && sample.getAdditions() > 0) {
                                sample.setScores(1L);
                            }

                            sample = sampleRepository.save(sample);
                            Pack pack = packRepository.findById(sample.getPackId()).orElse(null);

                            if (pack != null) {
                                pack.setScores(pack.getScores() + sample.getScores());
                                packRepository.save(pack);

                            } else {
                                throw new PackNotFoundException(
                                        format(PACK_NOT_FOUND.getMessage(), sample.getPackId())
                                );
                            }

                            IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
                            if (identityPrincipal != null && identityPrincipal.getAuthenticated()) {

                                Long subscriptionId = sampleRepository.findSubscriptionIdByIdentityId(identityPrincipal.getId());
                                if (subscriptionId != null) {
                                    sampleRepository.updateSubscriptionByDemotionScores(subscriptionId, sample.getScores());

                                } else {
                                    throw new RuntimeException("Отсутствует подписка пользователя");
                                }

                                if (!identitySampleRepository
                                                .existsIdentitySampleByIdentityIdAndSampleId(
                                                        identityPrincipal.getId(), sample.getId()
                                                )
                                ) {
                                    identitySampleRepository.save(
                                            IdentitySample.builder()
                                                    .identityId(identityPrincipal.getId())
                                                    .sampleId(sample.getId())
                                                    .build()
                                    );
                                } else {
                                    throw new SampleAlreadyAddedException(SAMPLE_ALREADY_ADDED.getMessage());
                                }

                            } else {
                                throw new IdentityNotAuthorizedException(NOT_AUTHORIZED.getMessage());
                            }

                            return format(SAMPLE_ADDED.getMessage(), sample.getId());
                        }
                )
                .orElseThrow(() -> new SampleNotFoundException(
                        format(SAMPLE_NOT_FOUND.getMessage(), sampleId))
                );
    }
}
