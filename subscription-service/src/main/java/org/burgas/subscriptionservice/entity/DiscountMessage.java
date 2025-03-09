package org.burgas.subscriptionservice.entity;

public enum DiscountMessage {

    DISCOUNT_CREATED("Скидка успешно создана"),
    OLD_DISCOUNT_NOT_FOUND("Скидка не найдена при окончании");

    private final String message;

    DiscountMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
