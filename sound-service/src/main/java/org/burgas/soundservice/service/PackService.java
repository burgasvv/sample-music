package org.burgas.soundservice.service;

import org.burgas.soundservice.dto.PackRequest;
import org.burgas.soundservice.dto.PackResponse;
import org.burgas.soundservice.exception.PackNotFoundException;
import org.burgas.soundservice.mapper.PackMapper;
import org.burgas.soundservice.repository.PackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static org.burgas.soundservice.entity.PackMessage.PACK_DELETED;
import static org.burgas.soundservice.entity.PackMessage.PACK_NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class PackService {

    private final PackRepository packRepository;
    private final PackMapper packMapper;

    public PackService(PackRepository packRepository, PackMapper packMapper) {
        this.packRepository = packRepository;
        this.packMapper = packMapper;
    }

    public List<PackResponse> findAll() {
        return this.packRepository
                .findAll()
                .stream()
                .map(packMapper::toPackResponse)
                .toList();
    }

    public List<PackResponse> findByGenreId(Long genreId) {
        return this.packRepository
                .findPacksByGenreId(genreId)
                .stream()
                .map(packMapper::toPackResponse)
                .toList();
    }

    public List<PackResponse> findByLabelId(Long labelId) {
        return this.packRepository
                .findPacksByLabelId(labelId)
                .stream()
                .map(packMapper::toPackResponse)
                .toList();
    }

    public PackResponse findById(Long packId) {
        return this.packRepository
                .findById(packId)
                .map(packMapper::toPackResponse)
                .orElseGet(PackResponse::new);
    }

    public PackResponse findByTitle(String title) {
        return this.packRepository
                .findPackByTitle(title)
                .map(packMapper::toPackResponse)
                .orElseGet(PackResponse::new);
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(final PackRequest packRequest) {
        return packMapper
                .toPackResponse(packRepository.save(
                        packMapper.toPack(packRequest)
                ))
                .getId();
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long packId) {
        return packRepository
                .findById(packId)
                .map(
                        pack -> {
                            packRepository.deleteById(pack.getId());
                            return String.format(PACK_DELETED.getMessage());
                        }
                )
                .orElseThrow(() -> new PackNotFoundException(
                        format(PACK_NOT_FOUND.getMessage(), packId))
                );
    }
}
