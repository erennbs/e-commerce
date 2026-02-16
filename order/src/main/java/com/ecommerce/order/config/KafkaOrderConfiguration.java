package com.ecommerce.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaOrderConfiguration {
    @Value("${spring.kafka.producer.bootstrap-server}")
    private String bootstrapServer;

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder
                .name("order-topic")
                .build();
    }
}
