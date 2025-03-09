package org.burgas.subscriptionservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic subscriptionPaymentTopic() {
        return TopicBuilder
                .name("subscription-payment-topic")
                .partitions(10)
                .replicas(10)
                .build();
    }
}
