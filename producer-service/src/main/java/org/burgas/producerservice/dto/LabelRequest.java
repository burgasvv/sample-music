package org.burgas.producerservice.dto;

public record LabelRequest(
        Long id,
        String title,
        String description,
        Long bossId,
        Long bannerId
) {
}
