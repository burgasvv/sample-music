package org.burgas.paymentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/payments")
@Tag(
        name = "PaymentController",
        description = "Контроллер для управления платежами и чеками пользователей"
)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/by-id")
    @Operation(summary = "Получение платежа по идентификатору")
    public @ResponseBody ResponseEntity<PaymentResponse> getPaymentById(
            @RequestParam Long paymentId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(paymentService.findById(paymentId, authentication));
    }
}
