package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceImplTest {
    private NotificationServiceImpl notificationService = new NotificationServiceImpl();

    @Test
    public void testSendNotification() {
        User user = new User("Test User", "test@example.com");
        assertTrue(notificationService.sendNotification(user, "Test message"));
    }

    @Test
    public void testSendNotificationNullUser() {
        assertFalse(notificationService.sendNotification(null, "Test message"));
    }

    @Test
    public void testSendNotificationEmptyMessage() {
        User user = new User("Test User", "test@example.com");
        assertFalse(notificationService.sendNotification(user, ""));
    }

    @Test
    public void testSendNotificationNullMessage() {
        User user = new User("Test User", "test@example.com");
        assertFalse(notificationService.sendNotification(user, null));
    }

    @Test
    public void testSendNotificationNullUserAndMessage() {
        assertFalse(notificationService.sendNotification(null, null));
    }
}


