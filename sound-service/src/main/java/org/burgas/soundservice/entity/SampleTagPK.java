package org.burgas.soundservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public final class SampleTagPK {

    private Long sampleId;
    private Long tagId;

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
}
