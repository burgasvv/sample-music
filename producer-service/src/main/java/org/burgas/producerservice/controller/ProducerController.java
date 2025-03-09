package org.burgas.producerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.producerservice.dto.ProducerRequest;
import org.burgas.producerservice.dto.ProducerResponse;
import org.burgas.producerservice.service.ProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;
import static org.burgas.producerservice.entity.ProducerMessage.PRODUCER_CREATED;
import static org.burgas.producerservice.entity.ProducerMessage.PRODUCER_UPDATED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/producers")
@Tag(
        name = "ProducerController",
        description = "Контроллер для управления и организации данных о продюсерах"
)
public final class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping(value = "/all")
    @Operation(
            summary = "Полный список продюсеров",
            description = "Получение полного списка продюсеров"
    )
    public @ResponseBody ResponseEntity<List<ProducerResponse>> getAllProducers() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(producerService.findAll());
    }

    @GetMapping(value = "/all/by-labelId")
    @Operation(
            summary = "Список продюсеров, состоящих в конкретном лейбле",
            description = "Получение списка продюсеров, состоящих в конкретном лейбле"
    )
    public @ResponseBody ResponseEntity<List<ProducerResponse>> getProducersByLabelId(@RequestParam Long labelId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(producerService.findProducersByLabelId(labelId));
    }

    @GetMapping(value = "/by-id", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Продюсер по идентификатору",
            description = "Получение продюсера по идентификатору"
    )
    public @ResponseBody ResponseEntity<ProducerResponse> getProducerById(@RequestParam Long producerId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(producerService.findById(producerId));
    }

    @Operation(
            summary = "Продюсер по идентификатору пользователя",
            description = "Получение продюсера по идентификатору пользователя"
    )
    @GetMapping(value = "/by-identity")
    public @ResponseBody ResponseEntity<ProducerResponse> getProducerByIdentityId(@RequestParam Long identityId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(producerService.findByIdentityId(identityId));
    }

    @PostMapping(value = "/create")
    public @ResponseBody ResponseEntity<String> createProducer(
            @RequestBody ProducerRequest producerRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        Long producerId = producerService.createOrUpdate(producerRequest, authentication);
        return ResponseEntity
                .status(OK)
                .body(format(PRODUCER_CREATED.getMessage(), producerId));
    }

    @PostMapping(value = "/update")
    public @ResponseBody ResponseEntity<String> updateProducer(
            @RequestBody ProducerRequest producerRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        Long producerId = producerService
                .createOrUpdate(producerRequest, authentication);
        return ResponseEntity
                .status(OK)
                .body(format(PRODUCER_UPDATED.getMessage(), producerId));
    }

    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deleteProducer(
            @RequestParam Long producerId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(producerService.deleteById(producerId, authentication));
    }
}
