package com.example;

public record User(String name, String email) {
    public User {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}




