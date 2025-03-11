package org.burgas.paymentservice.repository;

import org.burgas.paymentservice.entity.IdentityPaymentToken;
import org.burgas.paymentservice.entity.IdentityPaymentTokenPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityPaymentTokenRepository extends
        JpaRepository<IdentityPaymentToken, IdentityPaymentTokenPK> {
}
