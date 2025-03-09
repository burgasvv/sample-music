package org.burgas.soundservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(IdentitySamplePK.class)
public class IdentitySample {

    @Id private Long identityId;
    @Id private Long sampleId;

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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final IdentitySample identitySample;

        public Builder() {
            identitySample = new IdentitySample();
        }

        public Builder identityId(Long identityId) {
            this.identitySample.identityId = identityId;
            return this;
        }

        public Builder sampleId(Long sampleId) {
            this.identitySample.sampleId = sampleId;
            return this;
        }

        public IdentitySample build() {
            return identitySample;
        }
    }
}
