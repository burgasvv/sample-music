package org.burgas.subscriptionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.subscriptionservice.dto.SubscriptionRequest;
import org.burgas.subscriptionservice.dto.SubscriptionResponse;
import org.burgas.subscriptionservice.service.SubscriptionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burgas.subscriptionservice.entity.SubscriptionMessage.SUCCESS_SUBSCRIPTION;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Controller
@RequestMapping("/subscriptions")
@Tag(
        name = "SubscriptionController",
        description = "Контроллер для организации и обработки подписок"
)
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(
            summary = "Подписка по идентификатору",
            description = "Получение подписки по идентификатору"
    )
    @GetMapping(value = "/all")
    public @ResponseBody ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(subscriptionService.findAll());
    }

    @Operation(
            summary = "Подписка по идентификатору",
            description = "Получение подписки по идентификатору"
    )
    @GetMapping(value = "/by-id")
    public @ResponseBody ResponseEntity<SubscriptionResponse> getSubscriptionById(@RequestParam Long subscriptionId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(subscriptionService.findById(subscriptionId));
    }

    @Operation(
            summary = "Подписка по идентификатору пользователя",
            description = "Получение подписки по идентификатору пользователя"
    )
    @GetMapping(value = "/by-identity")
    public @ResponseBody ResponseEntity<SubscriptionResponse> getSubscriptionByIdentityId(@RequestParam Long identityId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(subscriptionService.findByIdentityId(identityId));
    }

    @Operation(
            summary = "Оформить подписку",
            description = "Оформить подписку в соответствии с данными аккаунта"
    )
    @PostMapping(value = "/subscribe")
    public @ResponseBody ResponseEntity<String> subscribeOnPlan(
            @RequestBody SubscriptionRequest subscriptionRequestMono,
            @RequestHeader(AUTHORIZATION) String authentication
    ) {
        subscriptionService.createOrUpdate(subscriptionRequestMono, authentication);
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(SUCCESS_SUBSCRIPTION.getMessage());
    }

    @Operation(
            summary = "Отменить подписку",
            description = "Отменить подписку в соответствии с данными аккаунта"
    )
    @PostMapping(value = "/cancel-subscription")
    public @ResponseBody ResponseEntity<String> cancelSubscription(
            @RequestParam Long identityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(subscriptionService.cancelSubscription(identityId, authentication));
    }

    @Operation(
            summary = "Возобновить подписку",
            description = "Возобновить подписку в соответствии с данными аккаунта"
    )
    @PostMapping(value = "/restore-subscription")
    public @ResponseBody ResponseEntity<String> restoreSubscription(
            @RequestParam Long identityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(subscriptionService.restoreSubscription(identityId, authentication));
    }
}
