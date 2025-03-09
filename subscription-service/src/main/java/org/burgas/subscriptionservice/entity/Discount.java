package org.burgas.subscriptionservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Discount {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long percentage;
    private LocalDate starts;
    private LocalDate ends;
    private Boolean actual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unused")
    public Long getPercentage() {
        return percentage;
    }

    @SuppressWarnings("unused")
    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    @SuppressWarnings("unused")
    public LocalDate getStarts() {
        return starts;
    }

    @SuppressWarnings("unused")
    public void setStarts(LocalDate starts) {
        this.starts = starts;
    }

    @SuppressWarnings("unused")
    public LocalDate getEnds() {
        return ends;
    }

    @SuppressWarnings("unused")
    public Boolean getActual() {
        return actual;
    }

    @SuppressWarnings("unused")
    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    @SuppressWarnings("unused")
    public void setEnds(LocalDate ends) {
        this.ends = ends;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Discount discount;

        public Builder() {
            discount = new Discount();
        }

        public Builder id(Long id) {
            this.discount.id = id;
            return this;
        }

        public Builder name(String name) {
            this.discount.name = name;
            return this;
        }

        public Builder description(String description) {
            this.discount.description = description;
            return this;
        }

        public Builder percentage(Long percentage) {
            this.discount.percentage = percentage;
            return this;
        }

        public Builder starts(LocalDate starts) {
            this.discount.starts = starts;
            return this;
        }

        public Builder ends(LocalDate ends) {
            this.discount.ends = ends;
            return this;
        }

        public Builder actual(Boolean actual) {
            this.discount.actual = actual;
            return this;
        }

        public Discount build() {
            return discount;
        }
    }
}
