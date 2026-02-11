package com.ecommerce.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;

@Configuration
public class KafkaPaymentConfig {
    @Value("${spring.kafka.producer.bootstrap-server}")
    private String bootstrapServer;

    @Bean
    public NewTopic paymentRequestTopic() {
        return TopicBuilder
                .name("payment-topic")
                .build();
    }

//    @Bean
//    public ProducerFactory<String, OrderConfirmation> orderProducerFactory() {
//        HashMap<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, OrderConfirmation> orderKafkaTemplate() {
//        return new KafkaTemplate<>(orderProducerFactory());
//    }
}
