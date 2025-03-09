package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;

    @SuppressWarnings("unused")
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
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Genre genre;

        public Builder() {
            genre = new Genre();
        }

        public Builder id(Long id) {
            this.genre.id = id;
            return this;
        }

        public Builder name(String name) {
            this.genre.name = name;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder description(String description) {
            this.genre.description = description;
            return this;
        }

        public Genre build() {
            return genre;
        }
    }
}
