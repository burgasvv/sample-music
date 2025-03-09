package org.burgas.paymentservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.burgas.paymentservice.dto.PaymentRequest;
import org.burgas.paymentservice.entity.Payment;
import org.burgas.paymentservice.mapper.PaymentMapper;
import org.burgas.paymentservice.repository.PaymentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaConsumer {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    public KafkaConsumer(PaymentMapper paymentMapper, PaymentRepository paymentRepository) {
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(groupId = "subscription-payment-group-id", topics = "subscription-payment-topic")
    public void receivePrivateMessage(ConsumerRecord<String, PaymentRequest> consumerRecord) {
        Payment payment = paymentMapper.toPaymentCreate(consumerRecord.value());
        paymentRepository.createPaymentIdentityToken(
                payment.getId(),
                UUID.randomUUID(),
                payment.getIdentityId()
        );
    }
}
