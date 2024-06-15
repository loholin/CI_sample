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
        User user = new User("Test User", "test@example.com");
        userManager.addUser(user, 100.0);

        assertEquals(user, userManager.getUser("test@example.com"));
        assertNotNull(userManager.getUserAccount("test@example.com"));
    }

    @Test
    public void testAddUserNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.addUser(null, 100.0);
        });
    }

    @Test
    public void testAddUserNegativeBalance() {
        User user = new User("Test User", "test@example.com");
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.addUser(user, -100.0);
        });
    }

    @Test
    public void testGetUserNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.getUser(null);
        });
    }

    @Test
    public void testGetUserEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.getUser("");
        });
    }

    @Test
    public void testGetUserNotFound() {
        assertNull(userManager.getUser("notfound@example.com"));
    }

    @Test
    public void testGetUserAccountNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.getUserAccount(null);
        });
    }

    @Test
    public void testGetUserAccountEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.getUserAccount("");
        });
    }

    @Test
    public void testGetUserAccountNotFound() {
        assertNull(userManager.getUserAccount("notfound@example.com"));
    }

    @Test
    public void testNotifyUser() {
        User user = new User("Test User", "test@example.com");
        assertTrue(userManager.notifyUser(user, notificationService, "Test message"));
    }

    @Test
    public void testNotifyUserNull() {
        assertFalse(userManager.notifyUser(null, notificationService, "Test message"));
    }

    @Test
    public void testNotifyUserNullService() {
        User user = new User("Test User", "test@example.com");
        assertFalse(userManager.notifyUser(user, null, "Test message"));
    }

    @Test
    public void testNotifyUserEmptyMessage() {
        User user = new User("Test User", "test@example.com");
        assertFalse(userManager.notifyUser(user, notificationService, ""));
    }

    @Test
    public void testNotifyUserNullMessage() {
        User user = new User("Test User", "test@example.com");
        assertFalse(userManager.notifyUser(user, notificationService, null));
    }

    @Test
    public void testProcessUserPaymentSuccess() {
        User user = new User("Test User", "test@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("test@example.com");

        assertTrue(userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService));
    }

    @Test
    public void testProcessUserPaymentNullAccount() {
        assertFalse(userManager.processUserPayment(null, 100.0, paymentService, notificationService));
    }

    @Test
    public void testProcessUserPaymentNullService() {
        User user = new User("Test User", "test@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("test@example.com");

        assertFalse(userManager.processUserPayment(userAccount, 100.0, null, notificationService));
    }

    @Test
    public void testProcessUserPaymentNullNotificationService() {
        User user = new User("Test User", "test@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("test@example.com");

        assertFalse(userManager.processUserPayment(userAccount, 100.0, paymentService, null));
    }

    @Test
    public void testProcessUserPaymentInsufficientBalance() {
        User user = new User("Test User", "test@example.com");
        userManager.addUser(user, 50.0);
        UserAccount userAccount = userManager.getUserAccount("test@example.com");

        assertFalse(userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService));
    }

    @Test
    public void testProcessUserPaymentNegativeAmount() {
        User user = new User("Test User", "test@example.com");
        userManager.addUser(user, 100.0);
        UserAccount userAccount = userManager.getUserAccount("test@example.com");

        assertFalse(userManager.processUserPayment(userAccount, -50.0, paymentService, notificationService));
    }

    @Test
    public void testProcessUserPaymentZeroAmount() {
        User user = new User("Test User", "test@example.com");
        userManager.addUser(user, 100.0);
        UserAccount userAccount = userManager.getUserAccount("test@example.com");

        assertFalse(userManager.processUserPayment(userAccount, 0.0, paymentService, notificationService));
    }
}


