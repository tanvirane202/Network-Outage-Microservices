package com.networkoutage.outage_service;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OutageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutageServiceApplication.class, args);
    }
}