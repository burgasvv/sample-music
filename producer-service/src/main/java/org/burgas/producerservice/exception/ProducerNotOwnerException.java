package org.burgas.producerservice.exception;

public class ProducerNotOwnerException extends RuntimeException {

    public ProducerNotOwnerException(String message) {
        super(message);
    }
}
