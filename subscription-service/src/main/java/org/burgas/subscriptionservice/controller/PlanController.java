package org.burgas.subscriptionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.subscriptionservice.dto.PlanRequest;
import org.burgas.subscriptionservice.dto.PlanResponse;
import org.burgas.subscriptionservice.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/plans")
@Tag(
        name = "PlanController",
        description = "Контроллер для управления платежными планами"
)
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @Operation(
            summary = "Платежный план по идентификатору",
            description = "Получение платежного плана по идентификатору"
    )
    @GetMapping(value = "/by-id", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<PlanResponse> getPlanById(@RequestParam Long planId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(planService.findById(planId));
    }

    @Operation(
            summary = "Платежный план по типу",
            description = "Получение платежного плана по типу"
    )
    @GetMapping(value = "/by-type", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<PlanResponse>> getPlansByType(@RequestParam String type) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(planService.findByType(type));
    }
    @Operation(
            summary = "Платежный план по периоду и типу",
            description = "Получение платежного плана по периоду и типу "
    )
    @GetMapping(value = "/by-period-type", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<PlanResponse>> getPlansByPeriodAndType(
            @RequestParam String period, @RequestParam String type
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(planService.findByPeriodAndType(period, type));
    }

    @Operation(
            summary = "Добавить платежный план",
            description = "Добавить платежный план и перенаправить на адрес объекта"
    )
    @PostMapping(value = "/create")
    public @ResponseBody ResponseEntity<Long> createPlan(@RequestBody PlanRequest planRequest) {
        return createOrUpdateAndRedirect(planRequest);
    }

    @Operation(
            summary = "Изменить платежный план",
            description = "Изменить платежный план и перенаправить на адрес объекта"
    )
    @PostMapping(value = "/update")
    public @ResponseBody ResponseEntity<Long> updatePlan(@RequestBody PlanRequest planRequest) {
        return createOrUpdateAndRedirect(planRequest);
    }

    private ResponseEntity<Long> createOrUpdateAndRedirect(PlanRequest planRequest) {
        Long planId = planService.createOrUpdate(planRequest);
        return ResponseEntity
                .status(FOUND)
                .location(create("/plans/by-id?planId=" + planId))
                .body(planId);
    }

    @Operation(summary = "Удалить платежный план")
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deletePlan(@RequestParam Long planId) {
        return ResponseEntity
                .status(OK)
                .body(planService.deleteById(planId));
    }
}
