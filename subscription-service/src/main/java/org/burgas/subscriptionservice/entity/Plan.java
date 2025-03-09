package org.burgas.subscriptionservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Plan implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private String period;
    private Long scores;
    private String type;
    private Long discountId;

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
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
    public String getPeriod() {
        return period;
    }

    @SuppressWarnings("unused")
    public void setPeriod(String period) {
        this.period = period;
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
    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    @SuppressWarnings("unused")
    public Long getDiscountId() {
        return discountId;
    }

    @SuppressWarnings("unused")
    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    @Override
    public String toString() {
        return "Plan{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", period='" + period + '\'' +
               ", scores=" + scores +
               ", type='" + type + '\'' +
               ", discountId=" + discountId +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Plan plan;

        public Builder() {
            plan = new Plan();
        }

        public Builder id(Long id) {
            this.plan.id = id;
            return this;
        }

        public Builder name(String name) {
            this.plan.name = name;
            return this;
        }

        public Builder price(Long price) {
            this.plan.price = price;
            return this;
        }

        public Builder period(String period) {
            this.plan.period = period;
            return this;
        }

        public Builder scores(Long scores) {
            this.plan.scores = scores;
            return this;
        }

        public Builder type(String type) {
            this.plan.type = type;
            return this;
        }

        public Builder discountId(Long discountId) {
            this.plan.discountId = discountId;
            return this;
        }

        public Plan build() {
            return plan;
        }
    }
}
