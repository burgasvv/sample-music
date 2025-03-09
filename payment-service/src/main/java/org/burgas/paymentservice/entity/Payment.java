package org.burgas.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Payment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long identityId;
    private Long subscriptionId;
    private Long price;
    private LocalDateTime paidAt;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    @SuppressWarnings("unused")
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    @SuppressWarnings("unused")
    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @SuppressWarnings("unused")
    public Long getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public void setPrice(Long price) {
        this.price = price;
    }

    @SuppressWarnings("unused")
    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    @SuppressWarnings("unused")
    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Payment payment;

        public Builder() {
            payment = new Payment();
        }

        public Builder id(Long id) {
            this.payment.id = id;
            return this;
        }

        public Builder identityId(Long identityId) {
            this.payment.identityId = identityId;
            return this;
        }

        public Builder subscriptionId(Long subscriptionId) {
            this.payment.subscriptionId = subscriptionId;
            return this;
        }

        public Builder price(Long price) {
            this.payment.price = price;
            return this;
        }

        public Builder paidAt(LocalDateTime paidAt) {
            this.payment.paidAt = paidAt;
            return this;
        }

        public Payment build() {
            return payment;
        }
    }
}
