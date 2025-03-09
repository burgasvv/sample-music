package org.burgas.producerservice.entity;

public enum ProducerMessage {

    PRODUCER_CREATED("Producer с идентификатором %s успешно создан"),
    PRODUCER_UPDATED("Producer с идентификатором %s успешно обновлен"),
    PRODUCER_DELETED("Аккаунт продюсера успешно удален"),
    PRODUCER_REGISTERED("Аккаунт продюсера зарегистрирован");

    private final String message;

    ProducerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
