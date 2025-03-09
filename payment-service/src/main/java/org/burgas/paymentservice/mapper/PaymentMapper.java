package org.burgas.paymentservice.mapper;

import org.burgas.paymentservice.dto.IdentityResponse;
import org.burgas.paymentservice.dto.PaymentRequest;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.dto.SubscriptionResponse;
import org.burgas.paymentservice.entity.Payment;
import org.burgas.paymentservice.handler.RestClientHandler;
import org.burgas.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.requireNonNull;

@Component
public class PaymentMapper {

    private final PaymentRepository paymentRepository;
    private final RestClientHandler restClientHandler;

    public PaymentMapper(PaymentRepository paymentRepository, RestClientHandler restClientHandler) {
        this.paymentRepository = paymentRepository;
        this.restClientHandler = restClientHandler;
    }

    public void toPaymentCreate(final PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .identityId(paymentRequest.identityId())
                .subscriptionId(paymentRequest.subscriptionId())
                .price(paymentRequest.price())
                .paidAt(LocalDateTime.now())
                .build();
        payment = paymentRepository.save(payment);

        paymentRepository.createPaymentIdentityToken(
                payment.getId(),
                UUID.randomUUID(),
                payment.getIdentityId()
        );
    }

    public PaymentResponse toPaymentResponse(final Payment payment, final String authentication) {
        UUID uuid = paymentRepository
                .findTokenByPaymentIdAndIdentityId(payment.getId(), payment.getIdentityId())
                .orElse(null);
        IdentityResponse identityResponse = restClientHandler
                .getIdentityResponseByPaymentToken(uuid)
                .getBody();
        SubscriptionResponse subscriptionResponse = restClientHandler
                .getSubscriptionResponseByIdentityId(requireNonNull(identityResponse).getId(), authentication)
                .getBody();
        return PaymentResponse.builder()
                .id(payment.getId())
                .identity(identityResponse)
                .subscription(subscriptionResponse)
                .price(payment.getPrice())
                .paidAt(payment.getPaidAt().format(ofPattern("dd.MM.yyyy, hh:mm")))
                .build();
    }
}
