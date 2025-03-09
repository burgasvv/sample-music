package org.burgas.identityserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.identityserver.dto.AuthorityRequest;
import org.burgas.identityserver.dto.AuthorityResponse;
import org.burgas.identityserver.service.AuthorityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping(value = "/authorities")
@Tag(
        name = "AuthorityController",
        description = "Контроллер для управление Authorities"
)
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Operation(
            summary = "Полный список Authorities",
            description = "Получение полного списка Authorities"
    )
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<AuthorityResponse>> getAllAuthorities() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(authorityService.findAll());
    }

    @Operation(
            summary = "Authority по идентификатору",
            description = "Получение Authority по идентификатору"
    )
    @GetMapping(value = "/by-id", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AuthorityResponse> getAuthorityById(@RequestParam Long authorityId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(authorityService.findById(authorityId));
    }

    @Operation(
            summary = "Authority по наименованию",
            description = "Получение Authority по наименованию"
    )
    @GetMapping(value = "/by-name", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AuthorityResponse> getAuthorityByName(@RequestParam String name) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(authorityService.findByName(name));
    }

    @Operation(
            summary = "Добавление Authority",
            description = "Добавление Authority и перенаправление на страницу объекта"
    )
    @PostMapping(value = "/create")
    public @ResponseBody ResponseEntity<Long> createAuthority(@RequestBody AuthorityRequest authorityRequestMono) {
        Long aLong = authorityService.createOrUpdate(authorityRequestMono);
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/authorities/by-id?authorityId=" + aLong))
                .body(aLong);
    }

    @Operation(
            summary = "Изменение Authority",
            description = "Изменение данных Authority и перенаправление на страницу объекта"
    )
    @PostMapping(value = "/update")
    public @ResponseBody ResponseEntity<Long> updateAuthority(@RequestBody AuthorityRequest authorityRequestMono) {
        Long aLong = authorityService.createOrUpdate(authorityRequestMono);
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/authorities/by-id?authorityId=" + aLong))
                .body(aLong);
    }

    @Operation(
            summary = "Удаление Authority",
            description = "Удаление данных Authority"
    )
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deleteAuthority(@RequestParam Long authorityId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(authorityService.deleteById(authorityId));
    }
}
