package org.burgas.imageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.imageservice.entity.Image;
import org.burgas.imageservice.exception.WrongContentTypeException;
import org.burgas.imageservice.service.ImageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burgas.imageservice.entity.ImageMessage.WRONG_CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Controller
@RequestMapping("/images")
@Tag(
        name = "ImageController",
        description = "Контроллер для управления данными изображений"
)
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(
            summary = "Изображение по идентификатору",
            description = "Получение изображение по идентификатору"
    )
    @GetMapping(value = "/by-id")
    public @ResponseBody ResponseEntity<Resource> getImageById(@RequestParam Long imageId) {
        Image image = imageService.findById(imageId);
        return ResponseEntity
                .status(OK)
                .contentType(MediaType.valueOf(image.getContentType()))
                .body(new InputStreamResource(new ByteArrayInputStream(image.getData())));
    }

    @Operation(
            summary = "Загрузка изображения",
            description = "Загрузка изображения в базу данных"
    )
    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> uploadImage(@RequestPart MultipartFile file) {
        if (
                "image".equals(Objects.requireNonNull(file.getContentType())
                        .split("/")[0])
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                    .body(imageService.uploadImage(file, null));
        } else {
            throw new WrongContentTypeException(WRONG_CONTENT_TYPE.getMessage());
        }
    }

    @Operation(
            summary = "Замена изображения",
            description = "Замена старого изображения на новое"
    )
    @PutMapping(value = "/change", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> changeImage(@RequestPart MultipartFile file, @RequestPart String imageId) {
        if (
                "image".equals(Objects.requireNonNull(file.getContentType())
                        .split("/")[0])
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                    .body(imageService.uploadImage(file, Long.valueOf(imageId)));
        } else {
            throw new WrongContentTypeException(WRONG_CONTENT_TYPE.getMessage());
        }
    }

    @Operation(summary = "Удаление изображения")
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deleteImage(@RequestParam Long imageId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(imageService.deleteImage(imageId));
    }

    @Operation(
            summary = "Загрузка изображения для пользователя",
            description = "Метод загрузки изображения для пользователя"
    )
    @PostMapping(value = "/identity/upload-image", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> uploadIdentityImage(
            @RequestPart MultipartFile file, @RequestPart String identityId
    ) {
        if (
                "image".equals(Objects.requireNonNull(file.getContentType())
                        .split("/")[0])
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                    .body(imageService.uploadIdentityImage(file, Long.valueOf(identityId),null));
        } else {
            throw new WrongContentTypeException(WRONG_CONTENT_TYPE.getMessage());
        }
    }

    @Operation(
            summary = "Смена изображения для пользователя",
            description = "Метод обновления изображения для пользователя"
    )
    @PutMapping(value = "/identity/change-image", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> changeIdentityImage(
            @RequestPart MultipartFile file, @RequestPart String identityId, @RequestPart String previousImageId
    ) {
        if (
                "image".equals(Objects.requireNonNull(file.getContentType())
                                .split("/")[0])
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                    .body(imageService.uploadIdentityImage(file, Long.valueOf(identityId),
                            Long.valueOf(previousImageId)));
        } else {
            throw new WrongContentTypeException(WRONG_CONTENT_TYPE.getMessage());
        }
    }

    @Operation(
            summary = "Удаление изображения пользователя",
            description = "Метод удаление изображения пользователя"
    )
    @DeleteMapping(value = "/identity/delete-image")
    public @ResponseBody ResponseEntity<String> deleteIdentityImage(@RequestParam Long imageId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(imageService.deleteImage(imageId));
    }

    @Operation(
            summary = "Загрузка изображения для лейбла",
            description = "Метод загрузки изображения для лейбла"
    )
    @PostMapping(value = "/label/upload-banner", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> uploadLabelBanner(
            @RequestPart MultipartFile file, @RequestPart String labelId
    ) {
        if (
                "image".equals(
                        Objects.requireNonNull(file.getContentType())
                                .split("/")[0]
                )
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                    .body(imageService.uploadLabelBanner(file, Long.valueOf(labelId), null));
        } else {
            throw new WrongContentTypeException(WRONG_CONTENT_TYPE.getMessage());
        }
    }

    @Operation(
            summary = "Смена изображения для лейбла",
            description = "Метод смены изображения для лейбла"
    )
    @PutMapping(value = "/label/change-banner", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> changeLabelBanner(
            @RequestPart MultipartFile file, @RequestPart String labelId, @RequestPart String previousImageId
    ) {
        if (
                "image".equals(
                        Objects.requireNonNull(file.getContentType())
                                .split("/")[0]
                )
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                    .body(imageService.uploadLabelBanner(file, Long.valueOf(labelId), Long.valueOf(previousImageId)));
        } else {
            throw new WrongContentTypeException(WRONG_CONTENT_TYPE.getMessage());
        }
    }

    @Operation(
            summary = "Удаление изображения лейбла",
            description = "Метод удаления изображения для лейбла"
    )
    @DeleteMapping(value = "/label/delete-banner")
    public @ResponseBody ResponseEntity<String> deleteLabelBanner(@RequestPart String imageId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(imageService.deleteImage(Long.valueOf(imageId)));
    }

    @Operation(
            summary = "Загрузка изображения для продюсера",
            description = "Метод загрузки изображения для продюсера"
    )
    @PostMapping(value = "/producer/upload-banner", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> uploadProducerBanner(
            @RequestPart MultipartFile file, @RequestPart String producerId
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(imageService.uploadProducerBanner(file, Long.valueOf(producerId), null));
    }

    @Operation(
            summary = "Смена изображения для продюсера",
            description = "Метод смены изображения для продюсера"
    )
    @PutMapping(value = "/producer/change-banner", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> changeProducerBanner(
            @RequestPart MultipartFile file, @RequestPart String producerId, @RequestPart String previousImageId
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(imageService.uploadProducerBanner(file, Long.valueOf(producerId), Long.valueOf(previousImageId)));
    }

    @Operation(
            summary = "Удаление изображения продюсера",
            description = "Метод удаления изображения для продюсера"
    )
    @DeleteMapping(value = "/producer/delete-banner")
    public @ResponseBody ResponseEntity<String> deleteProducerBanner(@RequestPart String imageId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(imageService.deleteImage(Long.valueOf(imageId)));
    }
}
