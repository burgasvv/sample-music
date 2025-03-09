package org.burgas.subscriptionservice.dto;

public final class DiscountResponse {

    private Long id;
    private String name;
    private String description;
    private Long percentage;
    private String starts;
    private String ends;
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
    public String getStarts() {
        return starts;
    }

    @SuppressWarnings("unused")
    public void setStarts(String starts) {
        this.starts = starts;
    }

    @SuppressWarnings("unused")
    public String getEnds() {
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
    public void setEnds(String ends) {
        this.ends = ends;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final DiscountResponse discountResponse;

        public Builder() {
            discountResponse = new DiscountResponse();
        }

        public Builder id(Long id) {
            this.discountResponse.id = id;
            return this;
        }

        public Builder name(String name) {
            this.discountResponse.name = name;
            return this;
        }

        public Builder description(String description) {
            this.discountResponse.description = description;
            return this;
        }

        public Builder percentage(Long percentage) {
            this.discountResponse.percentage = percentage;
            return this;
        }

        public Builder starts(String starts) {
            this.discountResponse.starts = starts;
            return this;
        }

        public Builder ends(String ends) {
            this.discountResponse.ends = ends;
            return this;
        }

        public Builder actual(Boolean actual) {
            this.discountResponse.actual = actual;
            return this;
        }

        public DiscountResponse build() {
            return discountResponse;
        }
    }
}
