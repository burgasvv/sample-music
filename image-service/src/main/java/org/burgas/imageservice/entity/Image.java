package org.burgas.imageservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String contentType;
    private Long size;
    private byte[] data;

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
    public String getContentType() {
        return contentType;
    }

    @SuppressWarnings("unused")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @SuppressWarnings("unused")
    public Long getSize() {
        return size;
    }

    @SuppressWarnings("unused")
    public void setSize(Long size) {
        this.size = size;
    }

    @SuppressWarnings("unused")
    public byte[] getData() {
        return data;
    }

    @SuppressWarnings("unused")
    public void setData(byte[] data) {
        this.data = data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Image image;

        public Builder() {
            image = new Image();
        }

        public Builder id(Long id) {
            this.image.id = id;
            return this;
        }

        public Builder name(String name) {
            this.image.name = name;
            return this;
        }

        public Builder contentType(String contentType) {
            this.image.contentType = contentType;
            return this;
        }

        public Builder size(Long size) {
            this.image.size = size;
            return this;
        }

        public Builder data(byte[] data) {
            this.image.data = data;
            return this;
        }

        public Image build() {
            return image;
        }
    }
}
