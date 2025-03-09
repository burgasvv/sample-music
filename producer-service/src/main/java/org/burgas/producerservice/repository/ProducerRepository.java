package org.burgas.producerservice.repository;

import org.burgas.producerservice.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    List<Producer> findProducersByLabelId(Long labelId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    insert into identity_producer_token(producer_id, token, identity_id) VALUES (:producerId, :token, :identityId)
                    """
    )
    void createProducerIdentityToken(Long producerId, UUID token, Long identityId);

    @Query(
            nativeQuery = true,
            value = """
                    select pit.token from identity_producer_token pit
                                     where producer_id = :producerId and identity_id = :identityId
                    """
    )
    Optional<UUID> findTokenByProducerIdAndIdentityId(Long producerId, Long identityId);

    Optional<Producer> findProducerByIdentityId(Long identityId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    update identity i set authority_id = :authorityId where i.id = :identityId
                    """
    )
    void updateIdentityBySettingAuthorityId(Long authorityId, Long identityId);
}
