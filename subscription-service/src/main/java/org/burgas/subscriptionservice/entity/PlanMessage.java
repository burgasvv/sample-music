package org.burgas.subscriptionservice.entity;

public enum PlanMessage {

    PLAN_NOT_FOUND_WITH_ID("Платежный план с идентификатором %s не найден"),
    PLAN_NOT_FOUND("Платежный план не найден"),
    PLAN_DELETED("Платежный план с идентификатором %s успешно удален");

    private final String message;

    PlanMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
