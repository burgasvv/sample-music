package org.burgas.paymentservice.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.burgas.paymentservice.dto.PaymentRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> consumerConfigs() {
        return Map.of(
                BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                GROUP_ID_CONFIG, "subscription-payment-group-id",
                KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                TYPE_MAPPINGS, "org.burgas.subscriptionservice.dto.PaymentRequest:org.burgas.paymentservice.dto.PaymentRequest"
        );
    }

    @Bean
    public ConsumerFactory<String, PaymentRequest> paymentRequestConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentRequestConsumerFactory());
        return factory;
    }
}
