package org.burgas.soundservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public final class IdentitySamplePK {

    private Long identityId;
    private Long sampleId;

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    @SuppressWarnings("unused")
    public Long getSampleId() {
        return sampleId;
    }

    @SuppressWarnings("unused")
    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }
}
