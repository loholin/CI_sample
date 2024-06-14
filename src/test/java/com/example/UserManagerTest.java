package com.example;
// src/test/java/com/example/UserManagerTest.java

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserManagerTest {
    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userManager = mock(UserManager.class);
    }

    @Test
    public void testAddUser() {
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user);

        verify(userManager, times(1)).addUser(user);
    }
}
