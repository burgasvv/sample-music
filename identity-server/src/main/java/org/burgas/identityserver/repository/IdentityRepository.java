package org.burgas.identityserver.repository;

import org.burgas.identityserver.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

    Optional<Identity> findIdentityByUsername(String username);

    @Query(
            nativeQuery = true,
            value = """
                    select i.* from identity i join identity_producer_token pit on i.id = pit.identity_id
                               where pit.token = :token
                    """
    )
    Optional<Identity> findIdentityByProducerToken(UUID token);

    @Query(
            nativeQuery = true,
            value = """
                    select i.* from identity i join identity_payment_token ipt on i.id = ipt.identity_id
                                        where ipt.token = :token
                    """
    )
    Optional<Identity> findIdentityByPaymentToken(UUID token);
}
