package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Pack implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long scores;
    private Long genreId;
    private Long imageId;
    private Long labelId;
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
    public Long getGenreId() {
        return genreId;
    }

    @SuppressWarnings("unused")
    public void setGenreId(Long genreId) {
        this.genreId = genreId;
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
    public Long getLabelId() {
        return labelId;
    }

    @SuppressWarnings("unused")
    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    @SuppressWarnings("unused")
    public Long getDemoId() {
        return demoId;
    }

    @SuppressWarnings("unused")
    public void setDemoId(Long demoId) {
        this.demoId = demoId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Pack pack;

        public Builder() {
            pack = new Pack();
        }

        public Builder id(Long id) {
            this.pack.id = id;
            return this;
        }

        public Builder title(String title) {
            this.pack.title = title;
            return this;
        }

        public Builder description(String description) {
            this.pack.description = description;
            return this;
        }

        public Builder scores(Long scores) {
            this.pack.scores = scores;
            return this;
        }

        public Builder genreId(Long genreId) {
            this.pack.genreId = genreId;
            return this;
        }

        public Builder imageId(Long imageId) {
            this.pack.imageId = imageId;
            return this;
        }

        public Builder labelId(Long labelId) {
            this.pack.labelId = labelId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder demoId(Long demoId) {
            this.pack.demoId = demoId;
            return this;
        }

        public Pack build() {
            return pack;
        }
    }
}
