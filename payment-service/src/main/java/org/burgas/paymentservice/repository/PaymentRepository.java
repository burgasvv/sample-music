package org.burgas.paymentservice.repository;

import org.burgas.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    insert into identity_payment_token(identity_id, payment_id, token) VALUES (?3, ?1, ?2)
                    """
    )
    void createPaymentIdentityToken(Long paymentId, UUID token, Long identityId);

    @Query(
            nativeQuery = true,
            value = """
                    select pit.token from identity_payment_token pit
                                     where payment_id = :paymentId and identity_id = :identityId
                    """
    )
    Optional<UUID> findTokenByPaymentIdAndIdentityId(Long paymentId, Long identityId);
}
