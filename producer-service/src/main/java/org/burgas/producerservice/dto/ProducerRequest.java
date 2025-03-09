package org.burgas.producerservice.dto;

public record ProducerRequest(
        Long id,
        String nickname,
        String firstname,
        String lastname,
        String patronymic,
        String phone,
        String about,
        Long identityId,
        Long labelId,
        Long bannerId
) {
}
