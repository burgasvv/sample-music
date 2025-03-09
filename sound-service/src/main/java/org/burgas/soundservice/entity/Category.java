package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Category {

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

         private final Category category;

        public Builder() {
            category = new Category();
        }

        public Builder id(Long id) {
            this.category.id = id;
            return this;
        }

        public Builder name(String name) {
            this.category.name = name;
            return this;
        }

        public Category build() {
            return category;
        }
    }
}
