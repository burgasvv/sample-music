package org.burgas.soundservice.repository;

import org.burgas.soundservice.entity.SampleTag;
import org.burgas.soundservice.entity.SampleTagPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleTagRepository extends JpaRepository<SampleTag, SampleTagPK> {
}
