package org.burgas.paymentservice.dto;

public final class PaymentResponse {

    private Long id;
    private IdentityResponse identity;
    private SubscriptionResponse subscription;
    private Long price;
    private String paidAt;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public IdentityResponse getIdentity() {
        return identity;
    }

    @SuppressWarnings("unused")
    public SubscriptionResponse getSubscription() {
        return subscription;
    }

    @SuppressWarnings("unused")
    public Long getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public String getPaidAt() {
        return paidAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final PaymentResponse paymentResponse;

        public Builder() {
            paymentResponse = new PaymentResponse();
        }

        public Builder id(Long id) {
            this.paymentResponse.id = id;
            return this;
        }

        public Builder identity(IdentityResponse identity) {
            this.paymentResponse.identity = identity;
            return this;
        }

        public Builder subscription(SubscriptionResponse subscription) {
            this.paymentResponse.subscription = subscription;
            return this;
        }

        public Builder price(Long price) {
            this.paymentResponse.price = price;
            return this;
        }

        public Builder paidAt(String paidAt) {
            this.paymentResponse.paidAt = paidAt;
            return this;
        }

        public PaymentResponse build() {
            return paymentResponse;
        }
    }
}
