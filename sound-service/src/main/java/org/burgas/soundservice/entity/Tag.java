package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Tag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

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

    public static final class Builder {

        private final Tag tag;

        public Builder() {
            tag = new Tag();
        }

        public Builder id(Long id) {
            this.tag.id = id;
            return this;
        }

        public Builder name(String name) {
            this.tag.name = name;
            return this;
        }

        public Tag build() {
            return tag;
        }
    }
}
