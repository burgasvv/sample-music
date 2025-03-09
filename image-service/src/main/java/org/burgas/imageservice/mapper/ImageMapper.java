package org.burgas.imageservice.mapper;

import org.burgas.imageservice.entity.Image;
import org.burgas.imageservice.repository.ImageRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public final class ImageMapper {

    private final ImageRepository imageRepository;

    public ImageMapper(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Image toImage(MultipartFile multipartFile, Long imageId) {
        Long id = getData(imageId, 0L);
        return imageRepository
                .findById(id)
                .map(
                        image -> {
                            try {
                                return Image.builder()
                                        .id(image.getId())
                                        .name(getData(multipartFile.getOriginalFilename(), image.getName()))
                                        .contentType(getData(multipartFile.getContentType(), image.getContentType()))
                                        .size(getData(multipartFile.getSize(), image.getSize()))
                                        .data(getData(multipartFile.getBytes(), image.getData()))
                                        .build();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .orElseGet(
                        () -> {
                            try {
                                return Image.builder()
                                        .name(multipartFile.getOriginalFilename())
                                        .contentType(multipartFile.getContentType())
                                        .size(multipartFile.getSize())
                                        .data(multipartFile.getBytes())
                                        .build();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }
}
