package com.networkoutage.Notification_service.controller;






import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @PostMapping
    public String sendNotification(@RequestBody String message) {
        return "Notification sent: " + message;
    }
}