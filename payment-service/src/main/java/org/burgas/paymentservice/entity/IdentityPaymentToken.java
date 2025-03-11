package org.burgas.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.UUID;

@Entity
@IdClass(IdentityPaymentTokenPK.class)
public final class IdentityPaymentToken {

    @Id private Long identityId;
    @Id private Long paymentId;
    private UUID token;

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    @SuppressWarnings("unused")
    public Long getPaymentId() {
        return paymentId;
    }

    @SuppressWarnings("unused")
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @SuppressWarnings("unused")
    public UUID getToken() {
        return token;
    }

    @SuppressWarnings("unused")
    public void setToken(UUID token) {
        this.token = token;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final IdentityPaymentToken identityPaymentToken;

        public Builder() {
            identityPaymentToken = new IdentityPaymentToken();
        }

        public Builder identityId(Long identityId) {
            this.identityPaymentToken.identityId = identityId;
            return this;
        }

        public Builder paymentId(Long paymentId) {
            this.identityPaymentToken.paymentId = paymentId;
            return this;
        }

        public Builder token(UUID token) {
            this.identityPaymentToken.token = token;
            return this;
        }

        public IdentityPaymentToken build() {
            return identityPaymentToken;
        }
    }
}
