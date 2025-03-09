package org.burgas.soundservice.entity;

public enum SampleMessage {

    EMPTY_FILE("Вы не добавили семпл или файл пуст"),
    SAMPLE_ADDED("Семпл с идентификатором %s успешно добавлен"),
    SAMPLE_UPDATED("Семпл с идентификатором %s успешно изменен"),
    SAMPLE_NOT_FOUND("Семпл с идентификатором %s не найден"),
    SAMPLE_ALREADY_ADDED("Данный семпл уже добавлен вами"),
    WRONG_AUTHORITY("Только продюсер может загружать семплы"),
    WRONG_FORMAT("Можно загружать семплы формата audio/wave");

    private final String message;

    SampleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
