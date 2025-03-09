package org.burgas.subscriptionservice.mapper;

import org.burgas.subscriptionservice.dto.PlanResponse;
import org.burgas.subscriptionservice.dto.SubscriptionRequest;
import org.burgas.subscriptionservice.dto.SubscriptionResponse;
import org.burgas.subscriptionservice.entity.Plan;
import org.burgas.subscriptionservice.entity.Subscription;
import org.burgas.subscriptionservice.exception.PlanNotFoundException;
import org.burgas.subscriptionservice.repository.PlanRepository;
import org.burgas.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.burgas.subscriptionservice.entity.PlanMessage.PLAN_NOT_FOUND;

@Component
public class SubscriptionMapper {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public SubscriptionMapper(
            SubscriptionRepository subscriptionRepository,
            PlanRepository planRepository, PlanMapper planMapper
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Subscription toSubscription(final SubscriptionRequest subscriptionRequest) {
        Long subscriptionId = getData(subscriptionRequest.id(), 0L);
        return subscriptionRepository
                .findById(subscriptionId)
                .map(
                        subscription -> planRepository
                                .findById(subscription.getPlanId())
                                .map(
                                        plan -> {
                                            LocalDate now = LocalDate.now();
                                            LocalDate nextPayment = getNextPayment(now, plan);
                                            Boolean cancelled = getData(subscriptionRequest.cancelled(), subscription.getCancelled());
                                            if (!cancelled) {
                                                return Subscription.builder()
                                                        .id(subscription.getId())
                                                        .planId(getData(subscriptionRequest.planId(), subscription.getPlanId()))
                                                        .scores(plan.getScores() + subscription.getScores())
                                                        .paidAt(now)
                                                        .nextPayment(nextPayment)
                                                        .cancelled(false)
                                                        .build();
                                            } else {
                                                return Subscription.builder()
                                                        .id(subscription.getId())
                                                        .planId(getData(subscriptionRequest.planId(), subscription.getPlanId()))
                                                        .scores(subscription.getScores())
                                                        .paidAt(now)
                                                        .nextPayment(null)
                                                        .cancelled(true)
                                                        .build();
                                            }
                                        }
                                )
                                .orElseThrow(() -> new PlanNotFoundException(PLAN_NOT_FOUND.getMessage()))
                )
                .orElseGet(
                        () -> planRepository
                                .findById(subscriptionRequest.planId())
                                .map(
                                        plan -> {
                                            LocalDate paidAt = LocalDate.now();
                                            LocalDate nextPayment = getNextPayment(paidAt, plan);
                                            return Subscription.builder()
                                                    .planId(plan.getId())
                                                    .paidAt(paidAt)
                                                    .scores(plan.getScores())
                                                    .nextPayment(nextPayment)
                                                    .cancelled(false)
                                                    .build();
                                        }
                                )
                                .orElseThrow(() -> new PlanNotFoundException(PLAN_NOT_FOUND.getMessage()))
                );
    }

    private static LocalDate getNextPayment(LocalDate now, Plan plan) {
        YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonth());
        LocalDate nextPayment;

        if (plan.getPeriod().equals("monthly")) {

            if (yearMonth.getMonthValue() == 12) {
                nextPayment = LocalDate.of(
                        yearMonth.getYear() + 1, 1, now.getDayOfMonth());
            } else {
                nextPayment = LocalDate.of(
                        yearMonth.getYear(), yearMonth.getMonthValue() + 1, now.getDayOfMonth()
                );
            }
            return nextPayment;

        } else {
            return LocalDate.of(
                    yearMonth.getYear() + 1, yearMonth.getMonthValue(), now.getDayOfMonth()
            );
        }
    }

    public SubscriptionResponse toSubscriptionResponse(final Subscription subscription) {
        Plan plan = planRepository.findById(subscription.getPlanId())
                .orElseGet(Plan::new);
        PlanResponse planResponse = planMapper.toPlanResponse(plan);
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .plan(planResponse)
                .scores(subscription.getScores())
                .paidAt(
                        subscription.getPaidAt()
                                .format(ofPattern("dd.MM.yy"))
                )
                .nextPayment(
                        subscription.getNextPayment()
                                .format(ofPattern("dd.MM.yy"))
                )
                .cancelled(subscription.getCancelled())
                .build();
    }
}
