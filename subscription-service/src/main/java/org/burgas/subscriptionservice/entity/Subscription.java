package org.burgas.subscriptionservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long planId;
    private Long scores;
    private LocalDate paidAt;
    private LocalDate nextPayment;
    private Boolean cancelled;

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Long getPlanId() {
        return planId;
    }

    @SuppressWarnings("unused")
    public void setPlanId(Long planId) {
        this.planId = planId;
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
    public LocalDate getPaidAt() {
        return paidAt;
    }

    @SuppressWarnings("unused")
    public void setPaidAt(LocalDate paidAt) {
        this.paidAt = paidAt;
    }

    @SuppressWarnings("unused")
    public LocalDate getNextPayment() {
        return nextPayment;
    }

    @SuppressWarnings("unused")
    public void setNextPayment(LocalDate nextPayment) {
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

        private final Subscription subscription;

        public Builder() {
            subscription = new Subscription();
        }

        public Builder id(Long id) {
            this.subscription.id = id;
            return this;
        }

        public Builder planId(Long planId) {
            this.subscription.planId = planId;
            return this;
        }

        public Builder scores(Long scores) {
            this.subscription.scores = scores;
            return this;
        }

        public Builder paidAt(LocalDate paidAt) {
            this.subscription.paidAt = paidAt;
            return this;
        }

        public Builder nextPayment(LocalDate nextPayment) {
            this.subscription.nextPayment = nextPayment;
            return this;
        }

        public Builder cancelled(Boolean cancelled) {
            this.subscription.cancelled = cancelled;
            return this;
        }

        public Subscription build() {
            return subscription;
        }
    }
}
