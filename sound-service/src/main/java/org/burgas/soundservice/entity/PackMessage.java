package org.burgas.soundservice.entity;

public enum PackMessage {

    WRONG_LABEL("Данный продюсер не является членом выбранного лейбла"),
    PACK_NOT_FOUND("Пакет петель с идентификатором %s не найден"),
    PACK_DELETED("Пакет петель с идентификатором %s успешно удален"),
    PACK_ID_NOT_SPECIFIED("Отсутствует идентификатор пакета"),
    NOT_AUTHORIZED("Пользователь не авторизован для совершения операции");

    private final String message;

    PackMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
