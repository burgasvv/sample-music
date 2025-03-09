package org.burgas.soundservice.exception;

public class IdentityNotAuthorizedException extends RuntimeException {

    public IdentityNotAuthorizedException(String message) {
        super(message);
    }
}
