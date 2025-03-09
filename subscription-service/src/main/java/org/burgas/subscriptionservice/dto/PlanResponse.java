package org.burgas.subscriptionservice.dto;

public final class PlanResponse {

    private Long id;
    private String name;
    private Long price;
    private String period;
    private Long scores;
    private String type;
    private Long discountId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final PlanResponse planResponse;

        public Builder() {
            planResponse = new PlanResponse();
        }

        public Builder id(Long id) {
            this.planResponse.id = id;
            return this;
        }

        public Builder name(String name) {
            this.planResponse.name = name;
            return this;
        }

        public Builder price(Long price) {
            this.planResponse.price = price;
            return this;
        }

        public Builder period(String period) {
            this.planResponse.period = period;
            return this;
        }

        public Builder scores(Long scores) {
            this.planResponse.scores = scores;
            return this;
        }

        public Builder type(String type) {
            this.planResponse.type = type;
            return this;
        }

        public Builder discountId(Long discountId) {
            this.planResponse.discountId = discountId;
            return this;
        }

        public PlanResponse build() {
            return planResponse;
        }
    }
}
