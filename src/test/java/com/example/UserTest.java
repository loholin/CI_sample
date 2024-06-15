package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testValidUser() {
        User user = new User("Alice", "alice@example.com");
        assertEquals("Alice", user.name());
        assertEquals("alice@example.com", user.email());
    }

    @Test
    public void testInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User("Bob", "bobexample.com");
        });
        assertEquals("Invalid email address", exception.getMessage());
    }
}
