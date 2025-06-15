package org.burgas.imageservice.service;

import org.burgas.imageservice.entity.Image;
import org.burgas.imageservice.exception.ImageNotFoundException;
import org.burgas.imageservice.mapper.ImageMapper;
import org.burgas.imageservice.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static java.lang.String.format;
import static org.burgas.imageservice.entity.ImageMessage.*;
import static org.springframework.transaction.annotation.Isolation.*;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    public Image findById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseGet(Image::new);
    }

    @Transactional(
            isolation = READ_COMMITTED, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadImage(MultipartFile multipartFile, Long previousImageId) {
        if (previousImageId == null) {
            Image saved = imageRepository.save(
                    imageMapper.toImage(multipartFile, null)
            );
            return format(IMAGE_UPLOADED.getMessage(), saved.getId());

        } else {

            Image saved = imageRepository.save(
                    imageMapper.toImage(multipartFile, previousImageId)
            );
            return format(IMAGE_CHANGED.getMessage(), saved.getId());
        }
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteImage(Long imageId) {
        return imageRepository
                .findById(imageId)
                .map(
                        image -> {
                            imageRepository.deleteById(image.getId());
                            return format(IMAGE_DELETED.getMessage(), image.getId());
                        }
                )
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND.getMessage()));
    }

    @Transactional(
            isolation = READ_COMMITTED, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadIdentityImage(
            final MultipartFile multipartFile, final Long identityId, final Long previousImageId
    ) {
        if (previousImageId == null) {
            Image saved = imageRepository.save(
                    imageMapper.toImage(multipartFile, null)
            );
            imageRepository.updateIdentityBySettingImageId(identityId, saved.getId());
            return format(IMAGE_UPLOADED.getMessage(), saved.getId());

        } else {
            return uploadImage(multipartFile, previousImageId);
        }
    }

    @Transactional(
            isolation = READ_COMMITTED, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadLabelBanner(
            final MultipartFile multipartFile, final Long labelId, final Long previousImageId
    ) {
        if (previousImageId == null) {
            Image saved = imageRepository.save(
                    imageMapper.toImage(multipartFile, null)
            );
            imageRepository.updateLabelBySettingBannerId(saved.getId(), labelId);
            return format(IMAGE_UPLOADED.getMessage(), saved.getId());

        } else {
            Image saved = imageRepository.save(
                    imageMapper.toImage(multipartFile, previousImageId)
            );
            return format(IMAGE_CHANGED.getMessage(), saved.getId());
        }
    }

    @Transactional(
            isolation = READ_COMMITTED, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadProducerBanner(
            final MultipartFile multipartFile, final Long producerId, final Long previousImageId
    ) {
        if (previousImageId == null) {
            Image saved = imageRepository.save(
                    imageMapper.toImage(multipartFile, null)
            );
            imageRepository.updateProducerBySettingBannerId(saved.getId(), producerId);
            return format(IMAGE_UPLOADED.getMessage(), saved.getId());

        } else {
            Image saved = imageRepository.save(
                    imageMapper.toImage(multipartFile, previousImageId)
            );
            return format(IMAGE_CHANGED.getMessage(), saved.getId());
        }
    }
}
