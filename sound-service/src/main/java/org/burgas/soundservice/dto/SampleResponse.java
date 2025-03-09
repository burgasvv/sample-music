package org.burgas.soundservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.burgas.soundservice.entity.Category;
import org.burgas.soundservice.entity.Key;

public final class SampleResponse {

    private Long id;
    private String name;
    private String contentType;
    private String format;
    private Long size;
    private Long additions;
    private Long bpm;
    private Key key;
    private Category category;
    private String packTitle;
    private Long scores;

    @JsonIgnore
    private byte[] data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

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
    public Long getSize() {
        return size;
    }

    @SuppressWarnings("unused")
    public void setSize(Long size) {
        this.size = size;
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

    @SuppressWarnings("unused")
    public Long getBpm() {
        return bpm;
    }

    @SuppressWarnings("unused")
    public void setBpm(Long bpm) {
        this.bpm = bpm;
    }

    @SuppressWarnings("unused")
    public Key getKey() {
        return key;
    }

    @SuppressWarnings("unused")
    public void setKey(Key key) {
        this.key = key;
    }

    @SuppressWarnings("unused")
    public Category getCategory() {
        return category;
    }

    @SuppressWarnings("unused")
    public void setCategory(Category category) {
        this.category = category;
    }

    @SuppressWarnings("unused")
    public String getPackTitle() {
        return packTitle;
    }

    @SuppressWarnings("unused")
    public void setPackTitle(String packTitle) {
        this.packTitle = packTitle;
    }

    @SuppressWarnings("unused")
    public Long getScores() {
        return scores;
    }

    @SuppressWarnings("unused")
    public void setScores(Long scores) {
        this.scores = scores;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final SampleResponse sampleResponse;

        public Builder() {
            sampleResponse = new SampleResponse();
        }

        public Builder id(Long id) {
            this.sampleResponse.id = id;
            return this;
        }

        public Builder name(String name) {
            this.sampleResponse.name = name;
            return this;
        }

        public Builder contentType(String contentType) {
            this.sampleResponse.contentType = contentType;
            return this;
        }

        public Builder format(String format) {
            this.sampleResponse.format = format;
            return this;
        }

        public Builder size(Long size) {
            this.sampleResponse.size = size;
            return this;
        }

        public Builder data(byte[] data) {
            this.sampleResponse.data = data;
            return this;
        }

        public Builder additions(Long additions) {
            this.sampleResponse.additions = additions;
            return this;
        }

        public Builder bpm(Long bpm) {
            this.sampleResponse.bpm = bpm;
            return this;
        }

        public Builder key(Key key) {
            this.sampleResponse.key = key;
            return this;
        }

        public Builder category(Category category) {
            this.sampleResponse.category = category;
            return this;
        }

        public Builder packTitle(String packTitle) {
            this.sampleResponse.packTitle = packTitle;
            return this;
        }

        public Builder scores(Long scores) {
            this.sampleResponse.scores = scores;
            return this;
        }

        public SampleResponse build() {
            return sampleResponse;
        }
    }
}
