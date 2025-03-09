package org.burgas.soundservice.repository;

import org.burgas.soundservice.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select i.subscription_id from identity i where i.id = ?1
                    """
    )
    Long findSubscriptionIdByIdentityId(Long identityId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    update subscription s set scores = scores - :sampleScores where s.id = :subscriptionId
                    """
    )
    void updateSubscriptionByDemotionScores(Long subscriptionId, Long sampleScores);

    List<Sample> findSamplesByPackId(Long packId);
}
