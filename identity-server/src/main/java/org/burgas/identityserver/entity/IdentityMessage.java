package org.burgas.identityserver.entity;

public enum IdentityMessage {

    IDENTITY_DELETED("Identity с идентификатором %s успешно удален"),
    IDENTITY_NOT_FOUND("Identity с идентификатором %s не найден"),
    IDENTITY_CREATED("Пользователь с идентификатором %s успешно создан"),
    IDENTITY_UPDATED("Пользователь с идентификатором %s успешно изменен"),
    IDENTITY_NOT_AUTHORIZED("Пользователь не авторизован и не аутентифицирован");

    private final String message;

    IdentityMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
