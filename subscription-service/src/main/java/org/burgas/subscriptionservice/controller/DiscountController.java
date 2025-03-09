package org.burgas.subscriptionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.subscriptionservice.dto.DiscountRequest;
import org.burgas.subscriptionservice.dto.DiscountResponse;
import org.burgas.subscriptionservice.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/discounts")
@Tag(
        name = "DiscountController",
        description = "Контроллер для управления скидками платежных планов"
)
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Operation(summary = "Получение полного списка скидок")
    @GetMapping(value = "/all")
    public @ResponseBody ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(discountService.findAll());
    }

    @Operation(summary = "Получение скидки по идентификатору")
    @GetMapping(value = "/by-id")
    public @ResponseBody ResponseEntity<DiscountResponse> getDiscountById(@RequestParam Long discountId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(discountService.findById(discountId));
    }

    @Operation(summary = "Добавление скидки")
    @PostMapping(value = "/create")
    public @ResponseBody ResponseEntity<Long> createDiscount(@RequestBody DiscountRequest discountRequest) {
        Long discountId = discountService.createDiscount(discountRequest);
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/discounts/by-id?discountId=" + discountId))
                .body(discountId);
    }
}
