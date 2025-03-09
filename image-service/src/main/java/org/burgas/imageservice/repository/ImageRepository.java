package org.burgas.imageservice.repository;

import org.burgas.imageservice.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    update identity i set image_id = :imageId where i.id = :identityId
                    """
    )
    void updateIdentityBySettingImageId(Long identityId, Long imageId);

    @Query(
            nativeQuery = true,
            value = """
                    select im.* from image im join identity i on im.id = i.image_id
                                        where i.id = ?1
                    """
    )
    Optional<Image> findImageByIdentityId(Long identityId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    update label l set banner_id = :bannerId where l.id = :labelId
                    """
    )
    void updateLabelBySettingBannerId(Long bannerId, Long labelId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    update producer p set banner_id = :bannerId where p.id = :producerId
                    """
    )
    void updateProducerBySettingBannerId(Long bannerId, Long producerId);
}
