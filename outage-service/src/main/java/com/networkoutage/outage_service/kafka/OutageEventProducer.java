package com.networkoutage.outage_service.kafka;



import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OutageEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutageEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void sendOutageEvent(String message) {

        kafkaTemplate.send("outage-events", message);

    }
}