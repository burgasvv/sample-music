package org.burgas.imageservice.exception;

public class NotOwnerOfLabelException extends RuntimeException {

    public NotOwnerOfLabelException(String message) {
        super(message);
    }
}
