package org.burgas.soundservice.entity;

public enum GenreMessage {

    GENRE_NOT_FOUND("Жанр музыки с идентификатором %s не был найден"),
    GENRE_DELETED("Жанр музыки с идентификатором %s был успешно удален");

    private final String message;

    GenreMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
