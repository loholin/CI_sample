package com.example;

// src/test/java/com/example/UserTest.java

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testValidUser() {
        User user = new User("Alice", "alice@example.com");
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    public void testInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User("Bob", "bobexample.com");
        });
        assertEquals("Invalid email address", exception.getMessage());
    }
}
