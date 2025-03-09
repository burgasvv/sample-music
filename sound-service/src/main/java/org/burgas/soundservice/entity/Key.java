package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Key {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String harmony;
    private String key;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getHarmony() {
        return harmony;
    }

    @SuppressWarnings("unused")
    public void setHarmony(String harmony) {
        this.harmony = harmony;
    }

    @SuppressWarnings("unused")
    public String getKey() {
        return key;
    }

    @SuppressWarnings("unused")
    public void setKey(String key) {
        this.key = key;
    }

    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    public static final class Builder {

        private final Key key;

        public Builder() {
            key = new Key();
        }

        public Builder id(Long id) {
            this.key.id = id;
            return this;
        }

        public Builder harmony(String harmony) {
            this.key.harmony = harmony;
            return this;
        }

        public Builder key(String key) {
            this.key.key = key;
            return this;
        }

        public Builder type(String type) {
            this.key.type = type;
            return this;
        }

        public Key build() {
            return key;
        }
    }
}
