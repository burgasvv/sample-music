package org.burgas.soundservice.repository;

import org.burgas.soundservice.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    Optional<Pack> findPackByTitle(String title);

    List<Pack> findPacksByGenreId(Long genreId);

    List<Pack> findPacksByLabelId(Long labelId);
}
