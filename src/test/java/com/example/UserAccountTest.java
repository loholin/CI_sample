package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserAccountTest {

    @Test
    public void testGetBalance() {
        User user = new User("Alice", "alice@example.com");
        UserAccount userAccount = new UserAccount(user, 100.0);
        assertEquals(100.0, userAccount.getBalance());
    }

    @Test
    public void testSetBalance() {
        User user = new User("Alice", "alice@example.com");
        UserAccount userAccount = new UserAccount(user, 100.0);
        userAccount.setBalance(200.0);
        assertEquals(200.0, userAccount.getBalance());
    }
}

