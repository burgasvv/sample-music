package org.burgas.soundservice.repository;

import org.burgas.soundservice.entity.IdentitySample;
import org.burgas.soundservice.entity.IdentitySamplePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentitySampleRepository extends JpaRepository<IdentitySample, IdentitySamplePK> {

    Boolean existsIdentitySampleByIdentityIdAndSampleId(Long identityId, Long sampleId);
}
