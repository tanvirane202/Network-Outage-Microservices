package com.networkguard.api_gateway1.security;





import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;



@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(jwtUtil);

        return http

                .csrf(csrf -> csrf.disable())

                .addFilterAt(
                        jwtAuthenticationFilter,
                        SecurityWebFiltersOrder.AUTHENTICATION
                )

                .authorizeExchange(exchange -> exchange

                       
                        .pathMatchers("/auth/**")
                        .permitAll()

                        
                        .pathMatchers("/admin/**")
                        .hasRole("ADMIN")

                        
                        .pathMatchers("/technicians/**")
                        .hasAnyRole("TECHNICIAN", "ADMIN")

                       
                        .pathMatchers("/customers/**")
                        .hasAnyRole("CUSTOMER", "ADMIN")

                      
                        .pathMatchers(HttpMethod.POST, "/outages")
                        .hasAnyRole("CUSTOMER", "ADMIN")

                       
                        .pathMatchers(
                                HttpMethod.GET,
                                "/outages",
                                "/outages/**"
                        )
                        .hasAnyRole(
                                "CUSTOMER",
                                "TECHNICIAN",
                                "ADMIN"
                        )

                       
                        .pathMatchers("/outages/*/assign/*")
                        .hasRole("ADMIN")

                        
                        .pathMatchers("/outages/*/investigate")
                        .hasAnyRole("TECHNICIAN", "ADMIN")

                     
                        .pathMatchers("/outages/*/progress")
                        .hasAnyRole("TECHNICIAN", "ADMIN")

                     
                        .pathMatchers("/outages/*/complete")
                        .hasAnyRole("TECHNICIAN", "ADMIN")

                       
                        .pathMatchers(HttpMethod.PUT, "/outages/*")
                        .hasRole("ADMIN")

                    
                        .pathMatchers(HttpMethod.DELETE, "/outages/*")
                        .hasRole("ADMIN")

                       
                        .anyExchange()
                        .authenticated()
                )

                .build();
    }
}