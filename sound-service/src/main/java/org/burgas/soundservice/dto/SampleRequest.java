package org.burgas.soundservice.dto;

public record SampleRequest(
        Long id,
        String name,
        Long bpm,
        Long keyId,
        Long categoryId,
        Long packId
) {
}
