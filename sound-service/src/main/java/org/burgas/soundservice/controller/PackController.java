package org.burgas.soundservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.soundservice.dto.PackRequest;
import org.burgas.soundservice.dto.PackResponse;
import org.burgas.soundservice.service.PackService;
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
@RequestMapping("/packs")
@Tag(
        name = "PackController",
        description = "Контроллер для управления и организации данных петлевых пакетов"
)
public class PackController {

    private final PackService packService;

    public PackController(PackService packService) {
        this.packService = packService;
    }

    @Operation(summary = "Получение полного списка пакетов петель")
    @GetMapping(value = "/all")
    public @ResponseBody ResponseEntity<List<PackResponse>> getAllPacks() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(packService.findAll());
    }

    @Operation(summary = "Получение пакетов петель определенного жанра")
    @GetMapping(value = "/by-genre")
    public @ResponseBody ResponseEntity<List<PackResponse>> getPackByGenreId(@RequestParam Long genreId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(packService.findByGenreId(genreId));
    }

    @Operation(summary = "Получение пакетов петель определенного лейбла")
    @GetMapping(value = "/by-label")
    public @ResponseBody ResponseEntity<List<PackResponse>> getPackByLabelId(@RequestParam Long labelId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(packService.findByLabelId(labelId));
    }

    @Operation(summary = "Получение пакета петель по идентификатору")
    @GetMapping(value = "/by-id")
    public @ResponseBody ResponseEntity<PackResponse> getPackById(@RequestParam Long packId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(packService.findById(packId));
    }

    @Operation(summary = "Получение пакета петель по наименованию")
    @GetMapping(value = "/by-title")
    public @ResponseBody ResponseEntity<PackResponse> getPackByTitle(@RequestParam String title) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(packService.findByTitle(title));
    }

    @Operation(summary = "Добавление пакета петель")
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Long> createPack(
            @RequestBody PackRequest packRequest, @SuppressWarnings("unused") @RequestParam Long labelId
    ) {
        Long packId = packService.createOrUpdate(packRequest);
        return ResponseEntity
                .status(FOUND)
                .location(create("/packs/by-id?packId=" + packId))
                .body(packId);
    }

    @Operation(summary = "Редактирование пакета петель")
    @PostMapping(value = "/update", consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Long> updatePack(
            @RequestBody PackRequest packRequest, @SuppressWarnings("unused") @RequestParam Long labelId
    ) {
        Long packId = packService.createOrUpdate(packRequest);
        return ResponseEntity
                .status(FOUND)
                .location(create("/packs/by-id?packId=" + packId))
                .body(packId);
    }

    @Operation(summary = "Удаление пакета петель")
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deletePack(@RequestParam Long packId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(packService.deleteById(packId));
    }
}
