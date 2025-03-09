package org.burgas.subscriptionservice.entity;

public enum SubscriptionMessage {

    IDENTITY_NOT_AUTHORIZED("Пользователь не авторизован и не аутентифицирован"),
    SUCCESS_SUBSCRIPTION("Вы успешно подписаны на платежный план"),
    SUBSCRIPTION_CANCELED("Подписка на платежный план отменена"),
    SUBSCRIPTION_ACCEPTED("Подписка на платежный план принята"),
    SUBSCRIPTION_AND_PLAN_NOT_CORRESPOND_YOUR_ACCOUNT("Данные подписки и платежного плана не соответствуют статусу аккаунта");

    private final String message;

    SubscriptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
