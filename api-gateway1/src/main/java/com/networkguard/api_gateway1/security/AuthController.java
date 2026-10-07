package com.networkguard.api_gateway1.security;



import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");

        String role = request.get("role").toUpperCase();

        Long technicianId = null;

        // Technician ID only for TECHNICIAN role
        if (role.equals("TECHNICIAN")) {

            String id = request.get("technicianId");

            if (id == null) {
                throw new RuntimeException(
                        "technicianId is required for TECHNICIAN login"
                );
            }

            technicianId = Long.parseLong(id);
        }

        String token = jwtUtil.generateToken(
                username,
                role,
                technicianId
        );

        return Map.of(
                "token", token,
                "username", username,
                "role", role
        );
    }
}