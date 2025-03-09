package org.burgas.soundservice.dto;

import org.burgas.soundservice.entity.Genre;

public final class PackResponse {

    private Long id;
    private String title;
    private String description;
    private Long scores;
    private Genre genre;
    private Long imageId;
    private String labelTitle;
    private Long demoId;

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
    public Long getScores() {
        return scores;
    }

    @SuppressWarnings("unused")
    public void setScores(Long scores) {
        this.scores = scores;
    }

    @SuppressWarnings("unused")
    public Genre getGenre() {
        return genre;
    }

    @SuppressWarnings("unused")
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @SuppressWarnings("unused")
    public Long getImageId() {
        return imageId;
    }

    @SuppressWarnings("unused")
    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @SuppressWarnings("unused")
    public String getLabelTitle() {
        return labelTitle;
    }

    @SuppressWarnings("unused")
    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    @SuppressWarnings("unused")
    public Long getDemoId() {
        return demoId;
    }

    @SuppressWarnings("unused")
    public void setDemoId(Long demoId) {
        this.demoId = demoId;
    }

    public static Builder build() {
        return new Builder();
    }

    public static final class Builder {

        private final PackResponse packResponse;

        public Builder() {
            packResponse = new PackResponse();
        }

        public Builder id(Long id) {
            this.packResponse.id = id;
            return this;
        }

        public Builder title(String title) {
            this.packResponse.title = title;
            return this;
        }

        public Builder description(String description) {
            this.packResponse.description = description;
            return this;
        }

        public Builder scores(Long scores) {
            this.packResponse.scores = scores;
            return this;
        }

        public Builder genre(Genre genre) {
            this.packResponse.genre = genre;
            return this;
        }

        public Builder imageId(Long imageId) {
            this.packResponse.imageId = imageId;
            return this;
        }

        public Builder labelTitle(String labelTitle) {
            this.packResponse.labelTitle = labelTitle;
            return this;
        }

        public Builder demoId(Long demoId) {
            this.packResponse.demoId = demoId;
            return this;
        }

        public PackResponse build() {
            return packResponse;
        }
    }
}
