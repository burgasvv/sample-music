package org.burgas.imageservice.exception;

public class WrongContentTypeException extends RuntimeException {

    public WrongContentTypeException(String message) {
        super(message);
    }
}
