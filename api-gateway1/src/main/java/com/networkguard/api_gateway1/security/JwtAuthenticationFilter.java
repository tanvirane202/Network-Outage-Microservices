package com.networkguard.api_gateway1.security;



import java.util.List;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            WebFilterChain chain) {

        String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return chain.filter(exchange);
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        Long technicianId = jwtUtil.getTechnicianId(token);

        System.out.println("JWT User: " + username);
        System.out.println("JWT Role: " + role);
        System.out.println("JWT Technician ID: " + technicianId);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + role
                                )
                        )
                );

        ServerHttpRequest request =
                exchange.getRequest()
                        .mutate()
                        .headers(headers -> {

                            if (role.equals("TECHNICIAN")
                                    && technicianId != null) {

                                headers.set(
                                        "X-Technician-Id",
                                        String.valueOf(technicianId)
                                );
                            }

                        })
                        .build();

        ServerWebExchange mutatedExchange =
                exchange
                        .mutate()
                        .request(request)
                        .build();

        System.out.println(
                "X-Technician-Id Header: "
                        + mutatedExchange.getRequest()
                                .getHeaders()
                                .getFirst("X-Technician-Id")
        );

        return chain
                .filter(mutatedExchange)
                .contextWrite(
                        ReactiveSecurityContextHolder
                                .withAuthentication(authentication)
                );
    }
}

