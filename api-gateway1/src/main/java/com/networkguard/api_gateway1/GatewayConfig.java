package com.networkguard.api_gateway1;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {

        System.out.println("===== GATEWAY ROUTE CONFIG LOADED =====");

        return builder.routes()

               
                .route("outage-service", r -> r
                        .path("/outages", "/outages/**")
                        .uri("lb://OUTAGE-SERVICE"))

                .route("customer-service", r -> r
                        .path("/customers", "/customers/**")
                        .uri("lb://CUSTOMER-SERVICE"))

             
                .route("technician-service", r -> r
                        .path("/technicians", "/technicians/**")
                        .uri("lb://TECHNICIAN-SERVICE"))

              
                .route("notification-service", r -> r
                        .path("/notifications", "/notifications/**")
                        .uri("lb://NOTIFICATION-SERVICE"))

                .build();
    }
}