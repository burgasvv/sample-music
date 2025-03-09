package org.burgas.identityserver.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.identityserver.dto.IdentityRequest;
import org.burgas.identityserver.dto.IdentityResponse;
import org.burgas.identityserver.service.IdentityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping(value = "/identities")
@Tag(
        name = "IdentityController",
        description = "Контроллер для управление Identities"
)
public class IdentityController {

    private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Operation(
            summary = "Полный список Identities",
            description = "Получение полного списка Identities"
    )
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<IdentityResponse>> getAllIdentities() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(identityService.findAll());
    }

    @Operation(
            summary = "Identity по идентификатору",
            description = "Получение Identity по идентификатору"
    )
    @GetMapping(value = "/by-id", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<IdentityResponse> getIdentityById(
            @RequestParam Long identityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(identityService.findById(identityId, authentication));
    }

    @Hidden
    @GetMapping(value = "/by-producer-token/{token}")
    public @ResponseBody ResponseEntity<IdentityResponse> getIdentityByProducerToken(@PathVariable UUID token) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(identityService.findByProducerToken(token));
    }

    @Hidden
    @GetMapping(value = "/by-payment-token/{token}")
    public @ResponseBody ResponseEntity<IdentityResponse> getIdentityByPaymentToken(@PathVariable UUID token) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(identityService.findByPaymentToken(token));
    }

    @Operation(
            summary = "Identity по наименованию",
            description = "Получение Identity по наименованию"
    )
    @GetMapping(value = "/by-username", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<IdentityResponse> getIdentityByUsername(
            @RequestParam String username, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(identityService.findByUsername(username, authentication));
    }

    @Operation(
            summary = "Добавление Identity",
            description = "Добавление Identity и перенаправление на страницу объекта"
    )
    @PostMapping(value = "/create")
    public @ResponseBody ResponseEntity<Long> createIdentity(
            @RequestBody IdentityRequest identityRequest,
            @RequestHeader(value = AUTHORIZATION, required = false) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(identityService.createOrUpdate(identityRequest, authentication));
    }

    @Operation(
            summary = "Изменение Identity",
            description = "Изменение данных Identity и перенаправление на страницу объекта"
    )
    @PostMapping(value = "/update")
    public @ResponseBody ResponseEntity<Long> updateIdentity(
            @RequestBody IdentityRequest identityRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(identityService.createOrUpdate(identityRequest, authentication));
    }

    @Operation(
            summary = "Удаление Identity",
            description = "Удаление данных Identity"
    )
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deleteById(
            @RequestParam Long identityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(identityService.deleteById(identityId, authentication));
    }
}
