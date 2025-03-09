package org.burgas.subscriptionservice.repository;

import org.burgas.subscriptionservice.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findPlansByType(String type);

    List<Plan> findPlansByPeriodAndType(String period, String type);
}
