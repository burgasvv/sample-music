package org.burgas.subscriptionservice.kafka;

import org.burgas.subscriptionservice.dto.PaymentRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static java.lang.String.*;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, PaymentRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentRequest(final PaymentRequest paymentRequest) {
        kafkaTemplate.send(
                "subscription-payment-topic",
                valueOf(paymentRequest.identityId() + paymentRequest.subscriptionId()),
                paymentRequest
        );
    }
}
