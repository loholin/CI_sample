package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
    private UserManager userManager;
    private NotificationServiceImpl notificationService;
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationServiceImpl();
        paymentService = new PaymentServiceImpl();
        userManager = new UserManager();
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
        NotificationService notificationServiceMock = mock(NotificationService.class);
        User user = new User("Eve", "eve@example.com");
        userManager.addUser(user, 300.0);

        when(notificationServiceMock.sendNotification(user, "Test message")).thenReturn(false);

        boolean result = userManager.notifyUser(user, notificationServiceMock, "Test message");

        assertFalse(result);
        verify(notificationServiceMock, times(1)).sendNotification(user, "Test message");
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

    @Test
    public void testProcessUserPaymentWithNullUserAccountAndNotificationService() {
        boolean result = userManager.processUserPayment(null, 100.0, paymentService, notificationService);
        assertFalse(result);
        verify(notificationService, times(1)).sendNotification(null, "Payment of 100.0 failed due to insufficient balance.");
    }


}



