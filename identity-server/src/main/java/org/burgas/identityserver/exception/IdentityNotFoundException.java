package org.burgas.identityserver.exception;

public class IdentityNotFoundException extends RuntimeException {

  public IdentityNotFoundException(String message) {
    super(message);
  }
}
