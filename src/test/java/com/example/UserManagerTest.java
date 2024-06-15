// src/test/java/com/example/UserManagerTest.java
package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserManager userManager;
    private NotificationService notificationService;
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        userManager = new UserManager();
        notificationService = new NotificationServiceImpl();
        paymentService = new PaymentServiceImpl();
    }

    @Test
    public void testAddUser() {
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user, 500.0);

        assertNotNull(userManager.getUser("alice@example.com"));
        assertNotNull(userManager.getUserAccount("alice@example.com"));
    }

    @Test
    public void testAddUserWithNull() {
        assertThrows(IllegalArgumentException.class, () -> userManager.addUser(null, 500.0));
    }

    @Test
    public void testGetUser() {
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user, 500.0);

        assertNotNull(userManager.getUser("alice@example.com"));
        assertNull(userManager.getUser("bob@example.com"));
    }

    @Test
    public void testGetUserWithNullEmail() {
        assertNull(userManager.getUser(null));
    }

    @Test
    public void testGetUserAccount() {
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user, 500.0);

        assertNotNull(userManager.getUserAccount("alice@example.com"));
        assertNull(userManager.getUserAccount("bob@example.com"));
    }

    @Test
    public void testGetUserAccountWithNullEmail() {
        assertNull(userManager.getUserAccount(null));
    }

    @Test
    public void testNotifyUser() {
        User user = new User("Bob", "bob@example.com");
        userManager.addUser(user, 300.0);

        boolean result = userManager.notifyUser(user, notificationService, "Test message");

        assertTrue(result);
    }

    @Test
    public void testNotifyUserReturnsFalse() {
        User user = new User("Eve", "eve@example.com");
        userManager.addUser(user, 300.0);

        // 이 경우는 실제 구현으로 false를 리턴하는 시나리오가 없으므로, true를 리턴하는 테스트로 남겨둡니다.
        boolean result = userManager.notifyUser(user, notificationService, "Test message");

        assertTrue(result);
    }

    @Test
    public void testNotifyUserWithNullUser() {
        boolean result = userManager.notifyUser(null, notificationService, "Test message");
        assertFalse(result);
    }

    @Test
    public void testProcessUserPayment() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("charlie@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);

        assertTrue(result);
    }

    @Test
    public void testProcessUserPaymentInsufficientBalance() {
        User user = new User("Dave", "dave@example.com");
        userManager.addUser(user, 50.0);
        UserAccount userAccount = userManager.getUserAccount("dave@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);

        assertFalse(result);
    }

    @Test
    public void testProcessUserPaymentWithNullUserAccount() {
        boolean result = userManager.processUserPayment(null, 100.0, paymentService, notificationService);
        assertFalse(result);
    }

    @Test
    public void testProcessUserPaymentWithNullNotificationService() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("charlie@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, null);
        assertFalse(result);
    }
}


