package org.burgas.identityserver.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public final class IdentityResponse implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Long imageId;
    private String registered;
    private AuthorityResponse authority;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(authority.getAuthority())
        );
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
    public String getRegistered() {
        return registered;
    }

    @SuppressWarnings("unused")
    public void setRegistered(String registered) {
        this.registered = registered;
    }

    @SuppressWarnings("unused")
    public AuthorityResponse getAuthority() {
        return authority;
    }

    @SuppressWarnings("unused")
    public void setAuthority(AuthorityResponse authority) {
        this.authority = authority;
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

        private final IdentityResponse identityResponse;

        public Builder() {
            identityResponse = new IdentityResponse();
        }

        public Builder id(Long id) {
            this.identityResponse.id = id;
            return this;
        }

        public Builder username(String username) {
            this.identityResponse.username = username;
            return this;
        }

        public Builder password(String password) {
            this.identityResponse.password = password;
            return this;
        }

        public Builder email(String email) {
            this.identityResponse.email = email;
            return this;
        }

        public Builder imageId(Long imageId) {
            this.identityResponse.imageId = imageId;
            return this;
        }

        public Builder registered(String registered) {
            this.identityResponse.registered = registered;
            return this;
        }

        public Builder authority(AuthorityResponse authority) {
            this.identityResponse.authority = authority;
            return this;
        }

        public Builder subscriptionId(Long subscriptionId) {
            this.identityResponse.subscriptionId = subscriptionId;
            return this;
        }

        public IdentityResponse build() {
            return identityResponse;
        }
    }
}
