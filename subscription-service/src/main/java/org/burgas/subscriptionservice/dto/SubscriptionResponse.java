package org.burgas.subscriptionservice.dto;

public final class SubscriptionResponse {

    private Long id;
    private PlanResponse plan;
    private Long scores;
    private String paidAt;
    private String nextPayment;
    private Boolean cancelled;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public PlanResponse getPlan() {
        return plan;
    }

    @SuppressWarnings("unused")
    public void setPlan(PlanResponse plan) {
        this.plan = plan;
    }

    @SuppressWarnings("unused")
    public Long getScores() {
        return scores;
    }

    @SuppressWarnings("unused")
    public void setScores(Long scores) {
        this.scores = scores;
    }

    @SuppressWarnings("unused")
    public String getPaidAt() {
        return paidAt;
    }

    @SuppressWarnings("unused")
    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    @SuppressWarnings("unused")
    public String getNextPayment() {
        return nextPayment;
    }

    @SuppressWarnings("unused")
    public void setNextPayment(String nextPayment) {
        this.nextPayment = nextPayment;
    }

    @SuppressWarnings("unused")
    public Boolean getCancelled() {
        return cancelled;
    }

    @SuppressWarnings("unused")
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final SubscriptionResponse subscriptionResponse;

        public Builder() {
            subscriptionResponse = new SubscriptionResponse();
        }

        public Builder id(Long id) {
            this.subscriptionResponse.id = id;
            return this;
        }

        public Builder plan(PlanResponse plan) {
            this.subscriptionResponse.plan = plan;
            return this;
        }

        public Builder scores(Long scores) {
            this.subscriptionResponse.scores = scores;
            return this;
        }

        public Builder paidAt(String paidAt) {
            this.subscriptionResponse.paidAt = paidAt;
            return this;
        }

        public Builder nextPayment(String nextPayment) {
            this.subscriptionResponse.nextPayment = nextPayment;
            return this;
        }

        public Builder cancelled(Boolean cancelled) {
            this.subscriptionResponse.cancelled = cancelled;
            return this;
        }

        public SubscriptionResponse build() {
            return subscriptionResponse;
        }
    }
}
