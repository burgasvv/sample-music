package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(SampleTagPK.class)
public class SampleTag {

    @Id private Long sampleId;
    @Id private Long tagId;

    @SuppressWarnings("unused")
    public Long getSampleId() {
        return sampleId;
    }

    @SuppressWarnings("unused")
    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    @SuppressWarnings("unused")
    public Long getTagId() {
        return tagId;
    }

    @SuppressWarnings("unused")
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final SampleTag sampleTag;

        public Builder() {
            sampleTag = new SampleTag();
        }

        public Builder sampleId(Long sampleId) {
            this.sampleTag.sampleId = sampleId;
            return this;
        }

        public Builder tagId(Long tagId) {
            this.sampleTag.tagId = tagId;
            return this;
        }

        public SampleTag build() {
            return sampleTag;
        }
    }
}
