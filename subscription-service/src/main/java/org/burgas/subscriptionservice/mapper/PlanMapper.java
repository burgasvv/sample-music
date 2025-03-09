package org.burgas.subscriptionservice.mapper;

import org.burgas.subscriptionservice.dto.PlanRequest;
import org.burgas.subscriptionservice.dto.PlanResponse;
import org.burgas.subscriptionservice.entity.Plan;
import org.burgas.subscriptionservice.repository.PlanRepository;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    private final PlanRepository planRepository;

    public PlanMapper(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Plan toPlan(final PlanRequest planRequest) {
        Long planId = getData(planRequest.id(), 0L);
        return planRepository
                .findById(planId)
                .map(
                        plan -> Plan.builder()
                                .id(plan.getId())
                                .name(getData(planRequest.name(), plan.getName()))
                                .period(getData(planRequest.period(), plan.getPeriod()))
                                .price(getData(planRequest.price(), plan.getPrice()))
                                .scores(getData(planRequest.scores(), plan.getScores()))
                                .type(getData(planRequest.type(), plan.getType()))
                                .discountId(getData(planRequest.discountId(), plan.getDiscountId()))
                                .build()
                )
                .orElseGet(
                        () -> Plan.builder()
                                .name(planRequest.name())
                                .scores(planRequest.scores())
                                .price(planRequest.price())
                                .period(planRequest.period())
                                .type(planRequest.type())
                                .discountId(planRequest.discountId())
                                .build()
                );
    }

    public PlanResponse toPlanResponse(final Plan plan) {
        return PlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .period(plan.getPeriod())
                .price(plan.getPrice())
                .scores(plan.getScores())
                .type(plan.getType())
                .discountId(plan.getDiscountId())
                .build();
    }
}
