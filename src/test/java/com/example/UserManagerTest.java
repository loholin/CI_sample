package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserManagerTest {
    private UserManager userManager;
    private PaymentService paymentService;
    private NotificationService notificationService;

    @Before
    public void setUp() {
        userManager = new UserManager();
        paymentService = new PaymentServiceImpl();
        notificationService = new NotificationServiceImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithNullUser() {
        userManager.addUser(null, 100.0);
    }

    @Test
    public void testAddUser() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 200.0);
        User fetchedUser = userManager.getUser("charlie@example.com");
        assertNotNull(fetchedUser);
        assertEquals("Charlie", fetchedUser.getName());
    }

    @Test
    public void testGetUserWithNonexistentEmail() {
        User user = userManager.getUser("nonexistent@example.com");
        assertNull(user);
    }

    @Test
    public void testGetUserAccountWithNullEmail() {
        UserAccount userAccount = userManager.getUserAccount(null);
        assertNull(userAccount);
    }

    @Test
    public void testGetUserAccountWithNonexistentEmail() {
        UserAccount userAccount = userManager.getUserAccount("nonexistent@example.com");
        assertNull(userAccount);
    }

    @Test
    public void testNotifyUser() {
        User user = new User("Charlie", "charlie@example.com");
        boolean result = userManager.notifyUser(user, notificationService, "Test message");
        assertTrue(result);
    }

    @Test
    public void testNotifyUserWithNullUser() {
        boolean result = userManager.notifyUser(null, notificationService, "Test message");
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
    public void testProcessUserPaymentWithInsufficientBalance() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 50.0);
        UserAccount userAccount = userManager.getUserAccount("charlie@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);
        assertFalse(result);
    }

    @Test
    public void testProcessUserPaymentSuccessfully() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("charlie@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);
        assertTrue(result);
    }
}
