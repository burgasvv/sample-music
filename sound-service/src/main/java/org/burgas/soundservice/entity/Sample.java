package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Sample {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String contentType;
    private String format;
    private Long size;
    private byte[] data;
    private Long additions;
    private Long bpm;
    private Long keyId;
    private Long categoryId;
    private Long packId;
    private Long scores;

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

    @SuppressWarnings("unused")
    public String getContentType() {
        return contentType;
    }

    @SuppressWarnings("unused")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @SuppressWarnings("unused")
    public String getFormat() {
        return format;
    }

    @SuppressWarnings("unused")
    public void setFormat(String format) {
        this.format = format;
    }

    @SuppressWarnings("unused")
    public Long getBpm() {
        return bpm;
    }

    @SuppressWarnings("unused")
    public void setBpm(Long bpm) {
        this.bpm = bpm;
    }

    @SuppressWarnings("unused")
    public Long getKeyId() {
        return keyId;
    }

    @SuppressWarnings("unused")
    public void setKeyId(Long keyId) {
        this.keyId = keyId;
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
    public Long getCategoryId() {
        return categoryId;
    }

    @SuppressWarnings("unused")
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @SuppressWarnings("unused")
    public Long getPackId() {
        return packId;
    }

    @SuppressWarnings("unused")
    public void setPackId(Long packId) {
        this.packId = packId;
    }

    @SuppressWarnings("unused")
    public Long getScores() {
        return scores;
    }

    @SuppressWarnings("unused")
    public void setScores(Long scores) {
        this.scores = scores;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @SuppressWarnings("unused")
    public Long getAdditions() {
        return additions;
    }

    @SuppressWarnings("unused")
    public void setAdditions(Long additions) {
        this.additions = additions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Sample sample;

        public Builder() {
            sample = new Sample();
        }

        public Builder id(Long id) {
            this.sample.id = id;
            return this;
        }

        public Builder name(String name) {
            this.sample.name = name;
            return this;
        }

        public Builder contentType(String contentType) {
            this.sample.contentType = contentType;
            return this;
        }

        public Builder format(String format) {
            this.sample.format = format;
            return this;
        }

        public Builder bpm(Long bpm) {
            this.sample.bpm = bpm;
            return this;
        }

        public Builder keyId(Long keyId) {
            this.sample.keyId = keyId;
            return this;
        }

        public Builder size(Long size) {
            this.sample.size = size;
            return this;
        }

        public Builder categoryId(Long categoryId) {
            this.sample.categoryId = categoryId;
            return this;
        }

        public Builder packId(long packId) {
            this.sample.packId = packId;
            return this;
        }

        public Builder scores(Long scores) {
            this.sample.scores = scores;
            return this;
        }

        public Builder data(byte[] data) {
            this.sample.data = data;
            return this;
        }

        public Builder additions(Long additions) {
            this.sample.additions = additions;
            return this;
        }

        public Sample build() {
            return sample;
        }
    }
}
