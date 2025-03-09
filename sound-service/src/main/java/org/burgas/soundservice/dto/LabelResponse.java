package org.burgas.soundservice.dto;

public final class LabelResponse {

    private Long id;
    private String title;
    private String description;
    private Long bossId;
    private Long bannerId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
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
    public Long getBossId() {
        return bossId;
    }

    @SuppressWarnings("unused")
    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    @SuppressWarnings("unused")
    public Long getBannerId() {
        return bannerId;
    }

    @SuppressWarnings("unused")
    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final LabelResponse labelResponse;

        public Builder() {
            labelResponse = new LabelResponse();
        }

        public Builder id(Long id) {
            this.labelResponse.id = id;
            return this;
        }

        public Builder title(String title) {
            this.labelResponse.title = title;
            return this;
        }

        public Builder description(String description) {
            this.labelResponse.description = description;
            return this;
        }

        public Builder bossId(Long bossId) {
            this.labelResponse.bossId = bossId;
            return this;
        }

        public Builder bannerId(Long bannerId) {
            this.labelResponse.bannerId = bannerId;
            return this;
        }

        public LabelResponse build() {
            return labelResponse;
        }
    }
}
