package org.burgas.subscriptionservice.service;

import org.burgas.subscriptionservice.dto.DiscountRequest;
import org.burgas.subscriptionservice.dto.DiscountResponse;
import org.burgas.subscriptionservice.entity.Discount;
import org.burgas.subscriptionservice.entity.Plan;
import org.burgas.subscriptionservice.mapper.DiscountMapper;
import org.burgas.subscriptionservice.repository.DiscountRepository;
import org.burgas.subscriptionservice.repository.PlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.burgas.subscriptionservice.entity.DiscountMessage.OLD_DISCOUNT_NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(readOnly = true, propagation = REQUIRED)
public class DiscountService {

    private static final Logger log = LoggerFactory.getLogger(DiscountService.class);
    public static final String LOG_MINUTES_UNTIL_ENDING =
            "DISCOUNT-SERVICE::Discount thread is started: Days till ending: {}";

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final PlanRepository planRepository;

    public DiscountService(
            DiscountRepository discountRepository,
            DiscountMapper discountMapper, PlanRepository planRepository
    ) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
        this.planRepository = planRepository;
    }

    public List<DiscountResponse> findAll() {
        return discountRepository
                .findAll()
                .stream()
                .map(discountMapper::toDiscountResponse)
                .toList();
    }

    public DiscountResponse findById(Long discountId) {
        return discountRepository
                .findById(discountId)
                .map(discountMapper::toDiscountResponse)
                .orElseGet(DiscountResponse::new);
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createDiscount(final DiscountRequest discountRequest) {
        Discount discount = discountRepository.save(discountMapper.toDiscount(discountRequest));
        discountRequest
                .planIds()
                .forEach(
                        planId -> {
                            Plan plan = planRepository.findById(planId).orElse(null);
                            if (plan != null) {
                                plan.setDiscountId(discount.getId());
                                Long discountPrice = plan.getPrice() * discount.getPercentage() / 100;
                                plan.setPrice(plan.getPrice() - discountPrice);
                                planRepository.save(plan);
                            }
                        }
                );

        CompletableFuture.runAsync(
                () -> {
                    try {
                        long days = ChronoUnit.DAYS.between(discount.getStarts(), discount.getEnds());
                        log.info(LOG_MINUTES_UNTIL_ENDING, days);
                        TimeUnit.DAYS.sleep(days);

                        Discount oldDiscount = discountRepository.findById(discount.getId()).orElse(null);
                        if (oldDiscount != null) {
                            oldDiscount.setActual(false);
                            discountRepository.save(oldDiscount);

                            discountRequest
                                    .planIds()
                                    .forEach(
                                            planId -> {
                                                Plan plan = planRepository.findById(planId).orElse(null);
                                                if (plan != null) {
                                                    long percentsNow = 100 - discount.getPercentage();
                                                    double onePercent = (double) plan.getPrice() / (double) percentsNow;
                                                    double discountPrice = onePercent * (double )discount.getPercentage();
                                                    plan.setPrice(plan.getPrice() + (long) discountPrice);
                                                    plan.setDiscountId(null);
                                                    planRepository.save(plan);
                                                }
                                            }
                                    );

                        } else {
                            log.warn(OLD_DISCOUNT_NOT_FOUND.getMessage());
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        return discount.getId();
    }
}
