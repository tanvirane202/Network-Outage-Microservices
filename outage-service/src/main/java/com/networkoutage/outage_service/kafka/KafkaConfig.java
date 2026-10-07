package com.networkoutage.outage_service.kafka;




import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka-3a7630e2-jakartaeeproject.e.aivencloud.com:11299"
        );

        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
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
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }
}