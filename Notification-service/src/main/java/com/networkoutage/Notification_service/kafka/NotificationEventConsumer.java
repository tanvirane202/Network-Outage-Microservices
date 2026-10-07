package com.networkoutage.Notification_service.kafka;



import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventConsumer {

    @KafkaListener(
        topics = "outage-events",
        groupId = "notification-group"
    )
    public void consumeOutageEvent(String message) {

        System.out.println(
            "Received outage event: " + message
        );
    }
}