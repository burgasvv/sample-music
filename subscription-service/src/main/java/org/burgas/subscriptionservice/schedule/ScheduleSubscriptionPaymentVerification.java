package org.burgas.subscriptionservice.schedule;

import org.burgas.subscriptionservice.entity.Plan;
import org.burgas.subscriptionservice.kafka.KafkaProducer;
import org.burgas.subscriptionservice.repository.PlanRepository;
import org.burgas.subscriptionservice.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static java.util.concurrent.TimeUnit.HOURS;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ScheduleSubscriptionPaymentVerification {

    private static final Logger log = getLogger(ScheduleSubscriptionPaymentVerification.class);
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public ScheduleSubscriptionPaymentVerification(
            SubscriptionRepository subscriptionRepository, PlanRepository planRepository
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Scheduled(timeUnit = HOURS, fixedRate = 24)
    public void scheduleSubscriptionPayment() {
        subscriptionRepository.findAll()
                .forEach(
                        subscription -> {
                            LocalDate today = now();

                            if (today.isEqual(subscription.getNextPayment())) {
                                Plan plan = planRepository.findById(subscription.getPlanId()).orElse(null);

                                if (plan != null) {

                                    if (plan.getPeriod().equals("monthly")) {

                                        if (today.getMonthValue() == 12) {
                                            subscription.setPaidAt(today);
                                            subscription.setNextPayment(
                                                    of(today.getYear() + 1, 1, today.getDayOfMonth())
                                            );
                                            subscriptionRepository.save(subscription);

                                        } else {
                                            subscription.setPaidAt(today);
                                            subscription.setNextPayment(
                                                    of(today.getYear(), today.getMonthValue() + 1, today.getDayOfMonth())
                                            );
                                            subscriptionRepository.save(subscription);
                                        }

                                    } else {
                                        subscription.setPaidAt(today);
                                        subscription.setNextPayment(
                                                of(today.getYear() + 1,
                                                        today.getMonthValue(), today.getDayOfMonth()
                                                )
                                        );
                                        subscriptionRepository.save(subscription);
                                    }

                                } else {
                                    log.warn("SCHEDULE-SUBSCRIPTION-PAYMENT::Can't find payment plan");
                                }
                            }
                        }
                );
    }
}
