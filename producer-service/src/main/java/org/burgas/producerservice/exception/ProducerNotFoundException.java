package org.burgas.producerservice.exception;

public class ProducerNotFoundException extends RuntimeException {

    public ProducerNotFoundException(String message) {
        super(message);
    }
}
