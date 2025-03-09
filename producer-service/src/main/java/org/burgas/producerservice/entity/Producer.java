package org.burgas.producerservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Producer implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nickname;
    private String firstname;
    private String lastname;
    private String patronymic;

    @Pattern(regexp = "(^$[0-9]{10})")
    private String phone;
    private String about;
    private Long identityId;
    private Long labelId;
    private Long bannerId;

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
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Producer producer;

        public Builder() {
            producer = new Producer();
        }

        public Builder id(Long id) {
            this.producer.id = id;
            return this;
        }

        public Builder nickname(String nickname) {
            this.producer.nickname = nickname;
            return this;
        }

        public Builder firstname(String firstname) {
            this.producer.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.producer.lastname = lastname;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.producer.patronymic = patronymic;
            return this;
        }

        public Builder phone(String phone) {
            this.producer.phone = phone;
            return this;
        }

        public Builder about(String about) {
            this.producer.about = about;
            return this;
        }

        public Builder identityId(Long identityId) {
            this.producer.identityId = identityId;
            return this;
        }

        public Builder labelId(Long labelId) {
            this.producer.labelId = labelId;
            return this;
        }

        public Builder bannerId(Long bannerId) {
            this.producer.bannerId = bannerId;
            return this;
        }

        public Producer build() {
            return producer;
        }
    }
}
