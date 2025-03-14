package org.burgas.paymentservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.burgas.paymentservice.dto.PaymentRequest;
import org.burgas.paymentservice.entity.Payment;
import org.burgas.paymentservice.mapper.PaymentMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final PaymentMapper paymentMapper;

    public KafkaConsumer(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    @KafkaListener(groupId = "subscription-payment-group-id", topics = "subscription-payment-topic")
    public Payment receivePrivateMessage(ConsumerRecord<String, PaymentRequest> consumerRecord) {
        return paymentMapper.toPaymentCreate(consumerRecord.value());
    }
}
