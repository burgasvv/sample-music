package org.burgas.identityserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public final class Identity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long imageId;
    private LocalDate registered;
    private Long authorityId;
    private Long subscriptionId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }

    @SuppressWarnings("unused")
    public void setUsername(String username) {
        this.username = username;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("unused")
    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("unused")
    public Long getImageId() {
        return imageId;
    }

    @SuppressWarnings("unused")
    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @SuppressWarnings("unused")
    public LocalDate getRegistered() {
        return registered;
    }

    @SuppressWarnings("unused")
    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    @SuppressWarnings("unused")
    public Long getAuthorityId() {
        return authorityId;
    }

    @SuppressWarnings("unused")
    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    @SuppressWarnings("unused")
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    @SuppressWarnings("unused")
    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Identity identity;

        public Builder() {
            identity = new Identity();
        }

        public Builder id(Long id) {
            this.identity.id = id;
            return this;
        }

        public Builder username(String username) {
            this.identity.username = username;
            return this;
        }

        public Builder password(String password) {
            this.identity.password = password;
            return this;
        }

        public Builder email(String email) {
            this.identity.email = email;
            return this;
        }

        public Builder imageId(Long imageId) {
            this.identity.imageId = imageId;
            return this;
        }

        public Builder registered(LocalDate registered) {
            this.identity.registered = registered;
            return this;
        }

        public Builder authorityId(Long authorityId) {
            this.identity.authorityId = authorityId;
            return this;
        }

        public Builder subscriptionId(Long subscriptionId) {
            this.identity.subscriptionId = subscriptionId;
            return this;
        }

        public Identity build() {
            return identity;
        }
    }
}
