package org.burgas.soundservice.mapper;

import org.burgas.soundservice.dto.SampleResponse;
import org.burgas.soundservice.entity.Category;
import org.burgas.soundservice.entity.Key;
import org.burgas.soundservice.entity.Pack;
import org.burgas.soundservice.entity.Sample;
import org.burgas.soundservice.repository.CategoryRepository;
import org.burgas.soundservice.repository.KeyRepository;
import org.burgas.soundservice.repository.PackRepository;
import org.springframework.stereotype.Component;

@Component
public class SampleMapper {

    private final PackRepository packRepository;
    private final KeyRepository keyRepository;
    private final CategoryRepository categoryRepository;

    public SampleMapper(
            PackRepository packRepository,
            KeyRepository keyRepository, CategoryRepository categoryRepository
    ) {
        this.packRepository = packRepository;
        this.keyRepository = keyRepository;
        this.categoryRepository = categoryRepository;
    }

    public SampleResponse toSampleResponse(final Sample sample) {
        return SampleResponse.builder()
                .id(sample.getId())
                .bpm(sample.getBpm())
                .name(sample.getName())
                .data(sample.getData())
                .size(sample.getSize())
                .scores(sample.getScores())
                .format(sample.getFormat())
                .additions(sample.getAdditions())
                .contentType(sample.getContentType())
                .key(keyRepository.findById(sample.getKeyId()).orElseGet(Key::new))
                .packTitle(packRepository.findById(sample.getId()).orElseGet(Pack::new).getTitle())
                .category(categoryRepository.findById(sample.getCategoryId()).orElseGet(Category::new))
                .build();
    }
}
