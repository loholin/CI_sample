package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
    private UserManager userManager;

    @Mock
    private NotificationService notificationService;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
    public void testNotifyUser() {
        User user = new User("Bob", "bob@example.com");
        userManager.addUser(user, 300.0);

        when(notificationService.sendNotification(user, "Test message")).thenReturn(true);

        boolean result = userManager.notifyUser(user, notificationService, "Test message");

        assertTrue(result);
        verify(notificationService, times(1)).sendNotification(user, "Test message");
    }

    @Test
    public void testProcessUserPayment() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("charlie@example.com");

        when(paymentService.processPayment(userAccount, 100.0)).thenReturn(true);

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);

        assertTrue(result);
        verify(paymentService, times(1)).processPayment(userAccount, 100.0);
        verify(notificationService, times(1)).sendNotification(user, "Payment of 100.0 processed successfully.");
    }

    @Test
    public void testProcessUserPaymentInsufficientBalance() {
        User user = new User("Dave", "dave@example.com");
        userManager.addUser(user, 50.0);
        UserAccount userAccount = userManager.getUserAccount("dave@example.com");

        when(paymentService.processPayment(userAccount, 100.0)).thenReturn(false);

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);

        assertFalse(result);
        verify(paymentService, times(1)).processPayment(userAccount, 100.0);
        verify(notificationService, times(1)).sendNotification(user, "Payment of 100.0 failed due to insufficient balance.");
    }
}

