package com.example.librarydemo.dtos;

import org.springframework.security.web.authentication.ott.DefaultGenerateOneTimeTokenRequestResolver;

public class LoginResponse {
    private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }

    // Getters and setters...
    public LoginResponse setToken(String token) {
        this.token = token;
        return this; // return the object for chaining
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    // Fluent setter
    public LoginResponse setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this; // return the object for chaining
    }
}