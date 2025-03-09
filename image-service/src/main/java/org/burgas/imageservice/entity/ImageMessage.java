package org.burgas.imageservice.entity;

public enum ImageMessage {

    IMAGE_UPLOADED("Изображение с идентификатором %s успешно загружено"),
    IMAGE_CHANGED("Изображение с идентификатором %s успешно обновлено"),
    IMAGE_DELETED("Изображение с идентификатором %s успешно удалено"),
    IMAGE_NOT_FOUND("Изображение не найдено"),
    WRONG_CONTENT_TYPE("Неверный тип предоставляемого файла"),
    PRODUCER_NOT_FOUND("Продюсер не найден"),
    PRODUCER_NOT_A_MEMBER_OF_LABEL("Продюсер не является членом лейбла"),
    PRODUCER_NOT_OWNER_OF_LABEL("Продюсер не является владельцем лейбла для совершения данной операции"),
    NOT_AUTHORIZED_NOT_AUTHENTICATED("Пользователь не авторизован и не аутентифицирован");

    private final String message;

    ImageMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
