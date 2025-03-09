package org.burgas.producerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.producerservice.dto.LabelRequest;
import org.burgas.producerservice.dto.LabelResponse;
import org.burgas.producerservice.service.LabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.net.URI.create;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping(value = "/labels")
@Tag(
        name = "LabelController",
        description = "Контроллер для управления и организации работы с лэйблами"
)
public final class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping(value = "/all")
    @Operation(
            summary = "Полный список Labels",
            description = "Получение полного списка Labels"
    )
    public @ResponseBody ResponseEntity<List<LabelResponse>> getAllLabels() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(labelService.findAll());
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Label по идентификатору",
            description = "Получение Label по идентификатору"
    )
    public @ResponseBody ResponseEntity<LabelResponse> getLabelById(@RequestParam Long labelId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(labelService.findById(labelId));
    }

    @GetMapping(value = "/by-title")
    @Operation(
            summary = "Label по наименованию",
            description = "Получение Label по наименованию"
    )
    public @ResponseBody ResponseEntity<LabelResponse> getLabelByTitle(@RequestParam String title) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(labelService.findByTitle(title));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление Label",
            description = "Добавление Label с перенаправлением на страницу объекта"
    )
    public @ResponseBody ResponseEntity<Long> createLabel(
            @RequestBody LabelRequest labelRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return createOrUpdateAndRedirect(labelRequest, authentication);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение Label",
            description = "Изменение Label с перенаправлением на страницу объекта"
    )
    public @ResponseBody ResponseEntity<Long> updateLabel(
            @RequestBody LabelRequest labelRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return createOrUpdateAndRedirect(labelRequest, authentication);
    }

    private ResponseEntity<Long> createOrUpdateAndRedirect(
            LabelRequest labelRequest, String authentication
    ) {
        Long labelId = labelService.createOrUpdate(labelRequest, authentication);
        return ResponseEntity
                .status(FOUND)
                .header(AUTHORIZATION, authentication)
                .location(create("/labels/by-id?labelId=" + labelId))
                .body(labelId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "Удаление Label")
    public @ResponseBody ResponseEntity<String> deleteLabel(
            @RequestParam Long labelId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .ok(labelService.deleteById(labelId, authentication));
    }
}
