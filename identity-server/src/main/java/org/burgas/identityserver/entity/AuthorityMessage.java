package org.burgas.identityserver.entity;

public enum AuthorityMessage {

    AUTHORITY_DELETED("Authority с идентификатором %s успешно удалена"),
    AUTHORITY_NOT_FOUND("Authority с идентификатором %s не найдена");

    private final String message;

    AuthorityMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
