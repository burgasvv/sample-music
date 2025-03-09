package org.burgas.imageservice.dto;

public final class ProducerResponse {

    private Long id;
    private String nickname;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String phone;
    private String about;
    private IdentityResponse identity;
    private Long labelId;
    private Long bannerId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getNickname() {
        return nickname;
    }

    @SuppressWarnings("unused")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @SuppressWarnings("unused")
    public String getFirstname() {
        return firstname;
    }

    @SuppressWarnings("unused")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @SuppressWarnings("unused")
    public String getLastname() {
        return lastname;
    }

    @SuppressWarnings("unused")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @SuppressWarnings("unused")
    public String getPatronymic() {
        return patronymic;
    }

    @SuppressWarnings("unused")
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @SuppressWarnings("unused")
    public String getPhone() {
        return phone;
    }

    @SuppressWarnings("unused")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SuppressWarnings("unused")
    public String getAbout() {
        return about;
    }

    @SuppressWarnings("unused")
    public void setAbout(String about) {
        this.about = about;
    }

    @SuppressWarnings("unused")
    public IdentityResponse getIdentity() {
        return identity;
    }

    @SuppressWarnings("unused")
    public void setIdentity(IdentityResponse identity) {
        this.identity = identity;
    }

    @SuppressWarnings("unused")
    public Long getLabelId() {
        return labelId;
    }

    @SuppressWarnings("unused")
    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    @SuppressWarnings("unused")
    public Long getBannerId() {
        return bannerId;
    }

    @SuppressWarnings("unused")
    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }

    @Override
    public String toString() {
        return "ProducerResponse{" +
               "id=" + id +
               ", nickname='" + nickname + '\'' +
               ", firstname='" + firstname + '\'' +
               ", lastname='" + lastname + '\'' +
               ", patronymic='" + patronymic + '\'' +
               ", phone='" + phone + '\'' +
               ", about='" + about + '\'' +
               ", identity=" + identity +
               ", labelId=" + labelId +
               ", bannerId=" + bannerId +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final ProducerResponse producerResponse;

        public Builder() {
            producerResponse = new ProducerResponse();
        }

        public Builder id(Long id) {
            this.producerResponse.id = id;
            return this;
        }

        public Builder nickname(String nickname) {
            this.producerResponse.nickname = nickname;
            return this;
        }

        public Builder firstname(String firstname) {
            this.producerResponse.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.producerResponse.lastname = lastname;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.producerResponse.patronymic = patronymic;
            return this;
        }

        public Builder phone(String phone) {
            this.producerResponse.phone = phone;
            return this;
        }

        public Builder about(String about) {
            this.producerResponse.about = about;
            return this;
        }

        public Builder identity(IdentityResponse identity) {
            this.producerResponse.identity = identity;
            return this;
        }

        public Builder labelId(Long labelId) {
            this.producerResponse.labelId = labelId;
            return this;
        }

        public Builder bannerId(Long bannerId) {
            this.producerResponse.bannerId = bannerId;
            return this;
        }

        public ProducerResponse build() {
            return producerResponse;
        }
    }
}
