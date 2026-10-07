package com.networkoutage.Notification_service.kafka;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka-3a7630e2-jakartaeeproject.e.aivencloud.com:11299"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                "security.protocol",
                "SASL_SSL"
        );

        config.put(
                "sasl.mechanism",
                "PLAIN"
        );

        config.put(
                "sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"name\" password=\"password"
                + "\";"
        );

        config.put(
                "ssl.truststore.type",
                "PEM"
        );

        try {

            Path caPath = Paths.get(
                    getClass()
                            .getClassLoader()
                            .getResource("ca.pem")
                            .toURI()
            );

            String caCertificate = Files.readString(caPath);

            config.put(
                    "ssl.truststore.certificates",
                    caCertificate
            );

        } catch (Exception e) {
            throw new RuntimeException("Could not load ca.pem", e);
        }

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}