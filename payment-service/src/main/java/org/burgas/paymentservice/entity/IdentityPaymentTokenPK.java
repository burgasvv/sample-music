package org.burgas.paymentservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public final class IdentityPaymentTokenPK {

    private Long identityId;
    private Long paymentId;

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public Long getPaymentId() {
        return paymentId;
    }
}
