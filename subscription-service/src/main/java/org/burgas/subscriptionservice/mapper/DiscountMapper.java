package org.burgas.subscriptionservice.mapper;

import org.burgas.subscriptionservice.dto.DiscountRequest;
import org.burgas.subscriptionservice.dto.DiscountResponse;
import org.burgas.subscriptionservice.entity.Discount;
import org.burgas.subscriptionservice.repository.DiscountRepository;
import org.springframework.stereotype.Component;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public class DiscountMapper {

    private final DiscountRepository discountRepository;

    public DiscountMapper(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Discount toDiscount(final DiscountRequest discountRequest) {
        Long discountId = getData(discountRequest.id(), 0L);
        return discountRepository
                .findById(discountId)
                .map(
                        discount -> Discount.builder()
                                .id(discount.getId())
                                .name(getData(discountRequest.name(), discount.getName()))
                                .description(getData(discountRequest.description(), discount.getDescription()))
                                .percentage(getData(discountRequest.percentage(), discount.getPercentage()))
                                .starts(getData(discountRequest.starts(), discount.getStarts()))
                                .ends(getData(discountRequest.ends(), discount.getEnds()))
                                .actual(getData(discountRequest.actual(), discount.getActual()))
                                .build()
                )
                .orElseGet(
                        () -> Discount.builder()
                                .name(discountRequest.name())
                                .description(discountRequest.description())
                                .percentage(discountRequest.percentage())
                                .starts(discountRequest.starts())
                                .ends(discountRequest.ends())
                                .actual(true)
                                .build()
                );
    }

    public DiscountResponse toDiscountResponse(final Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .name(discount.getName())
                .description(discount.getDescription())
                .percentage(discount.getPercentage())
                .starts(discount.getStarts().format(ofPattern("dd.MM.yy")))
                .ends(discount.getEnds().format(ofPattern("dd.MM.yy")))
                .actual(discount.getActual())
                .build();
    }
}
