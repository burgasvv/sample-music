package org.burgas.producerservice.mapper;

import org.burgas.producerservice.dto.LabelRequest;
import org.burgas.producerservice.dto.LabelResponse;
import org.burgas.producerservice.entity.Label;
import org.burgas.producerservice.repository.LabelRepository;
import org.springframework.stereotype.Component;

@Component
public final class LabelMapper {

    private final LabelRepository labelRepository;

    public LabelMapper(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Label toLabel(final LabelRequest labelRequest) {
        Long labelId = getData(labelRequest.id(), 0L);
        return labelRepository
                .findById(labelId)
                .map(
                        label -> Label.builder()
                                .id(label.getId())
                                .title(getData(labelRequest.title(), label.getTitle()))
                                .description(getData(labelRequest.description(), label.getDescription()))
                                .bossId(getData(labelRequest.bossId(), label.getBossId()))
                                .bannerId(getData(labelRequest.bannerId(), label.getBannerId()))
                                .build()
                )
                .orElseGet(
                        () -> Label.builder()
                                .title(labelRequest.title())
                                .description(labelRequest.description())
                                .bannerId(labelRequest.bannerId())
                                .bossId(labelRequest.bossId())
                                .build()
                );
    }

    public LabelResponse toLabelResponse(final Label label) {
        return LabelResponse.builder()
                .id(label.getId())
                .title(label.getTitle())
                .description(label.getDescription())
                .bannerId(label.getBannerId())
                .bossId(label.getBossId())
                .build();
    }
}
