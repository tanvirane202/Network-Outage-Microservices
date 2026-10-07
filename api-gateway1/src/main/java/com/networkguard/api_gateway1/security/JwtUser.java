package com.networkguard.api_gateway1.security;



public class JwtUser {

    private String username;
    private String role;

    public JwtUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}