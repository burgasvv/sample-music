package org.burgas.soundservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.soundservice.dto.SampleRequest;
import org.burgas.soundservice.dto.SampleResponse;
import org.burgas.soundservice.exception.WrongFormatException;
import org.burgas.soundservice.service.SampleService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static org.burgas.soundservice.entity.SampleMessage.WRONG_FORMAT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping("/samples")
@Tag(
        name = "SampleController",
        description = "Контроллер для управления семплами"
)
public class SampleController {

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @Operation(summary = "Получение списка семплов по идентификатору пакета")
    @GetMapping(value = "/by-pack")
    public @ResponseBody ResponseEntity<List<SampleResponse>> getSamplesByPackId(@RequestParam Long packId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(sampleService.findByPackId(packId));
    }

    @Operation(summary = "Получение семпла по идентификатору")
    @GetMapping(value = "/by-id")
    public @ResponseBody ResponseEntity<Resource> getSampleById(@RequestParam Long sampleId) {
        SampleResponse sample = sampleService.findById(sampleId);
        return ResponseEntity
                .status(OK)
                .contentType(valueOf(sample.getContentType()))
                .body(new InputStreamResource(
                        new ByteArrayInputStream(sample.getData()))
                );
    }

    @Operation(summary = "Загрузка семпла продюсером")
    @PostMapping(value = "/upload")
    public @ResponseBody ResponseEntity<String> uploadSample(
            @RequestPart MultipartFile file, @RequestPart String packId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        if (
                "audio".equalsIgnoreCase(requireNonNull(file.getContentType()).split("/")[0]) &&
                "wave".equalsIgnoreCase(file.getContentType().split("/")[1])
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                    .body(sampleService.uploadSample(file, Long.valueOf(packId), authentication));
        } else {
            throw new WrongFormatException(WRONG_FORMAT.getMessage());
        }
    }

    @Operation(summary = "Обновление семпла продюсером")
    @PutMapping(value = "/update")
    public @ResponseBody ResponseEntity<String> updateSample(@RequestBody SampleRequest sampleRequest) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(sampleService.updateSample(sampleRequest));
    }

    @Operation(summary = "Добавление семпла пользователем и оплата кредитами")
    @PostMapping(value = "/add")
    public @ResponseBody ResponseEntity<String> addSample(
            @RequestParam Long sampleId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(sampleService.addSample(sampleId, authentication));
    }
}
