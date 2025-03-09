package org.burgas.soundservice.mapper;

import org.burgas.soundservice.dto.LabelResponse;
import org.burgas.soundservice.dto.PackRequest;
import org.burgas.soundservice.dto.PackResponse;
import org.burgas.soundservice.entity.Genre;
import org.burgas.soundservice.entity.Pack;
import org.burgas.soundservice.handler.RestClientHandler;
import org.burgas.soundservice.repository.GenreRepository;
import org.burgas.soundservice.repository.PackRepository;
import org.springframework.stereotype.Component;

@Component
public class PackMapper {

    private final PackRepository packRepository;
    private final GenreRepository genreRepository;
    private final RestClientHandler restClientHandler;

    public PackMapper(
            PackRepository packRepository, GenreRepository genreRepository,
            RestClientHandler restClientHandler
    ) {
        this.packRepository = packRepository;
        this.genreRepository = genreRepository;
        this.restClientHandler = restClientHandler;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Pack toPack(final PackRequest packRequest) {
        Long packId = getData(packRequest.id(), 0L);
        return packRepository
                .findById(packId)
                .map(
                        pack -> Pack.builder()
                                .id(pack.getId())
                                .title(getData(packRequest.title(), pack.getTitle()))
                                .description(getData(packRequest.description(), pack.getDescription()))
                                .genreId(getData(packRequest.genreId(), pack.getGenreId()))
                                .imageId(getData(packRequest.imageId(), pack.getImageId()))
                                .labelId(getData(packRequest.labelId(), pack.getLabelId()))
                                .scores(pack.getScores())
                                .build()
                )
                .orElseGet(
                        () -> Pack.builder()
                                .title(packRequest.title())
                                .description(packRequest.description())
                                .labelId(packRequest.labelId())
                                .imageId(packRequest.imageId())
                                .genreId(packRequest.genreId())
                                .scores(0L)
                                .build()
                );
    }

    public PackResponse toPackResponse(final Pack pack) {
        LabelResponse labelResponse = restClientHandler.getLabelById(pack.getLabelId()).getBody();
        return PackResponse.build()
                .id(pack.getId())
                .title(pack.getTitle())
                .description(pack.getDescription())
                .scores(pack.getScores())
                .imageId(pack.getImageId())
                .demoId(pack.getDemoId())
                .genre(genreRepository.findById(pack.getGenreId()).orElseGet(Genre::new))
                .labelTitle(labelResponse != null ? labelResponse.getTitle() : null)
                .build();
    }
}
