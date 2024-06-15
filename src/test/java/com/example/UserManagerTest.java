package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    @Test
    public void testAddUser() {
        UserManager userManager = new UserManager();
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user);

        assertNotNull(userManager.getUser("alice@example.com"));
    }

    @Test
    public void testGetUser() {
        UserManager userManager = new UserManager();
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user);

        User retrievedUser = userManager.getUser("alice@example.com");
        assertEquals("Alice", retrievedUser.getName());
    }
}

