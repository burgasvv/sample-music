package org.burgas.soundservice.dto;

public record PackRequest(
        Long id,
        String title,
        String description,
        Long genreId,
        Long imageId,
        Long labelId
) {
}
