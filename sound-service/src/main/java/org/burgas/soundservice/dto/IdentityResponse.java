package org.burgas.soundservice.dto;

public final class IdentityResponse  {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Long imageId;
    private String registered;
    private AuthorityResponse authority;
    private Long planId;

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
    public Long getPlanId() {
        return planId;
    }

    @SuppressWarnings("unused")
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "IdentityResponse{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", email='" + email + '\'' +
               ", imageId=" + imageId +
               ", registered='" + registered + '\'' +
               ", authority=" + authority +
               ", planId=" + planId +
               '}';
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

        @SuppressWarnings("unused")
        public Builder username(String username) {
            this.identityResponse.username = username;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder password(String password) {
            this.identityResponse.password = password;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder email(String email) {
            this.identityResponse.email = email;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder imageId(Long imageId) {
            this.identityResponse.imageId = imageId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder registered(String registered) {
            this.identityResponse.registered = registered;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder authority(AuthorityResponse authority) {
            this.identityResponse.authority = authority;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder planId(Long planId) {
            this.identityResponse.planId = planId;
            return this;
        }

        public IdentityResponse build() {
            return identityResponse;
        }
    }
}
