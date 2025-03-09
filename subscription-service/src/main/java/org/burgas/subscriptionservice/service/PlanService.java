package org.burgas.subscriptionservice.service;

import org.burgas.subscriptionservice.dto.PlanRequest;
import org.burgas.subscriptionservice.dto.PlanResponse;
import org.burgas.subscriptionservice.mapper.PlanMapper;
import org.burgas.subscriptionservice.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static org.burgas.subscriptionservice.entity.PlanMessage.PLAN_DELETED;
import static org.burgas.subscriptionservice.entity.PlanMessage.PLAN_NOT_FOUND_WITH_ID;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(readOnly = true, propagation = REQUIRED)
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public PlanService(PlanRepository planRepository, PlanMapper planMapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }

    public PlanResponse findById(Long planId) {
        return planRepository
                .findById(planId)
                .map(planMapper::toPlanResponse)
                .orElseGet(PlanResponse::new);
    }

    public List<PlanResponse> findByType(String type) {
        return planRepository
                .findPlansByType(type)
                .stream()
                .map(planMapper::toPlanResponse)
                .toList();
    }

    public List<PlanResponse> findByPeriodAndType(String period, String type) {
        return planRepository
                .findPlansByPeriodAndType(period, type)
                .stream()
                .map(planMapper::toPlanResponse)
                .toList();
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(PlanRequest planRequest) {
        return planMapper
                .toPlanResponse(planRepository.save(planMapper.toPlan(planRequest)))
                .getId();
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long planId) {
        return planRepository
                .findById(planId)
                .map(
                        plan -> {
                            planRepository.deleteById(Objects.requireNonNull(plan.getId()));
                            return format(PLAN_DELETED.getMessage(), plan.getId());
                        }

                )
                .orElseThrow(() -> new RuntimeException(format(PLAN_NOT_FOUND_WITH_ID.getMessage(), planId)));

    }
}
