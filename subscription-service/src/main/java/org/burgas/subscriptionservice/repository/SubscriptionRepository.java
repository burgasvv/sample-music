package org.burgas.subscriptionservice.repository;

import org.burgas.subscriptionservice.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select s.* from subscription s join identity i on s.id = i.subscription_id
                    where i.id = :identityId
                    """
    )
    Optional<Subscription> findSubscriptionByIdentityId(Long identityId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    update identity i set subscription_id = :subscriptionId where i.id = :identityId
                    """
    )
    void updateIdentityBySettingSubscriptionId(Long subscriptionId, Long identityId);
}
