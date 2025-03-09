package org.burgas.producerservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Label implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long bossId;
    private Long bannerId;

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

        private final Label label;

        public Builder() {
            label = new Label();
        }

        public Builder id(Long id) {
            this.label.id = id;
            return this;
        }

        public Builder title(String title) {
            this.label.title = title;
            return this;
        }

        public Builder description(String description) {
            this.label.description = description;
            return this;
        }

        public Builder bossId(Long bossId) {
            this.label.bossId = bossId;
            return this;
        }

        public Builder bannerId(Long bannerId) {
            this.label.bannerId = bannerId;
            return this;
        }

        public Label build() {
            return label;
        }
    }
}
