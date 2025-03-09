package org.burgas.subscriptionservice.service;

import org.burgas.subscriptionservice.dto.*;
import org.burgas.subscriptionservice.entity.Plan;
import org.burgas.subscriptionservice.entity.Subscription;
import org.burgas.subscriptionservice.entity.SubscriptionMessage;
import org.burgas.subscriptionservice.exception.IdentityNotAuthorizedException;
import org.burgas.subscriptionservice.exception.NotCorrespondDataException;
import org.burgas.subscriptionservice.exception.PlanNotFoundException;
import org.burgas.subscriptionservice.handler.RestClientHandler;
import org.burgas.subscriptionservice.kafka.KafkaProducer;
import org.burgas.subscriptionservice.mapper.SubscriptionMapper;
import org.burgas.subscriptionservice.repository.PlanRepository;
import org.burgas.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.burgas.subscriptionservice.entity.PlanMessage.PLAN_NOT_FOUND;
import static org.burgas.subscriptionservice.entity.SubscriptionMessage.*;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(readOnly = true, propagation = REQUIRED)
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final RestClientHandler restClientHandler;
    private final PlanRepository planRepository;
    private final KafkaProducer kafkaProducer;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            SubscriptionMapper subscriptionMapper, RestClientHandler restClientHandler,
            PlanRepository planRepository, KafkaProducer kafkaProducer
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.restClientHandler = restClientHandler;
        this.planRepository = planRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public List<SubscriptionResponse> findAll() {
        return subscriptionRepository
                .findAll()
                .stream()
                .map(subscriptionMapper::toSubscriptionResponse)
                .toList();
    }

    public SubscriptionResponse findById(Long subscriptionId) {
        return subscriptionRepository
                .findById(subscriptionId)
                .map(subscriptionMapper::toSubscriptionResponse)
                .orElseGet(SubscriptionResponse::new);
    }

    public SubscriptionResponse findByIdentityId(Long identityId) {
        Subscription subscription = subscriptionRepository
                .findSubscriptionByIdentityId(identityId)
                .orElse(null);

        return subscriptionMapper
                .toSubscriptionResponse(requireNonNull(subscription));
    }

    @SuppressWarnings("UnusedReturnValue")
    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(final SubscriptionRequest subscriptionRequest, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        if (
                requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(subscriptionRequest.identityId())
        ) {
            Plan plan = planRepository.findById(subscriptionRequest.planId()).orElse(null);
            ProducerResponse producerResponse = restClientHandler.getProducerByIdentityId(identityPrincipal.getId(), authentication).getBody();

            if (requireNonNull(plan).getId() != null) {

                if (requireNonNull(producerResponse).getId() != null) {

                    if (plan.getType().equals("producer") &&
                        producerResponse.getIdentity().getAuthority().getName().equals("ROLE_PRODUCER")) {

                        return subscribeOrUpdate(subscriptionRequest);

                    } else {
                        throw new NotCorrespondDataException(
                                SUBSCRIPTION_AND_PLAN_NOT_CORRESPOND_YOUR_ACCOUNT.getMessage());
                    }

                } else if (plan.getType().equals("user") && identityPrincipal.getAuthority().equals("ROLE_USER")) {
                    return subscribeOrUpdate(subscriptionRequest);
                }

                else {
                    throw new NotCorrespondDataException(
                            SUBSCRIPTION_AND_PLAN_NOT_CORRESPOND_YOUR_ACCOUNT.getMessage());
                }

            } else {
                throw new PlanNotFoundException(PLAN_NOT_FOUND.getMessage());
            }

        } else {
            throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED.getMessage());
        }
    }

    private Long subscribeOrUpdate(SubscriptionRequest subscriptionRequest) {
        Subscription saved = subscriptionRepository.save(subscriptionMapper.toSubscription(subscriptionRequest));
        subscriptionRepository.updateIdentityBySettingSubscriptionId(
                saved.getId(), subscriptionRequest.identityId()
        );
        SubscriptionResponse subscriptionResponse = subscriptionMapper.toSubscriptionResponse(saved);
        PaymentRequest paymentRequest = new PaymentRequest(
                null, subscriptionRequest.identityId(), subscriptionResponse.getId(), subscriptionResponse.getPlan().getPrice()
        );

        kafkaProducer.sendPaymentRequest(paymentRequest);
        return subscriptionResponse.getId();
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String cancelSubscription(Long identityId, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        Subscription subscription = subscriptionRepository.findSubscriptionByIdentityId(identityId)
                .orElse(null);

        return cancelOrRestoreSubscription(
                requireNonNull(identityPrincipal), identityId, subscription,
                true, SUBSCRIPTION_CANCELED
        );
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String restoreSubscription(Long identityId, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        Subscription subscription = subscriptionRepository.findSubscriptionByIdentityId(identityId).orElse(null);

        return cancelOrRestoreSubscription(
                requireNonNull(identityPrincipal), identityId, subscription,
                false, SUBSCRIPTION_ACCEPTED
        );
    }

    private String cancelOrRestoreSubscription(
            IdentityPrincipal identityPrincipal, Long identityId, Subscription subscription,
            boolean cancelled, SubscriptionMessage subscriptionCanceled
    ) {
        if (identityPrincipal.getAuthenticated() && identityPrincipal.getId().equals(identityId)) {

            subscription.setCancelled(cancelled);
            subscriptionRepository.save(subscription);
            return subscriptionCanceled.getMessage();

        } else {
            throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED.getMessage());
        }
    }
}
