// src/main/java/com/example/User.java
package com.example;

public class User {
    private String name;
    private String email;

    public User(String name, String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}



