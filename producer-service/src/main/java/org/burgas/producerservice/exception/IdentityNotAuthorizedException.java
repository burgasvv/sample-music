package org.burgas.producerservice.exception;

public class IdentityNotAuthorizedException extends RuntimeException {

    public IdentityNotAuthorizedException(String message) {
        super(message);
    }
}
