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

    // 사용자를 추가하고 제대로 추가되었는지 확인하는 테스트
    @Test
    public void testAddUser() {
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user, 500.0);

        assertNotNull(userManager.getUser("alice@example.com"));
        assertNotNull(userManager.getUserAccount("alice@example.com"));
    }

    // null 사용자를 추가하려고 할 때 IllegalArgumentException이 발생하는지 확인하는 테스트
    @Test
    public void testAddUserWithNull() {
        assertThrows(IllegalArgumentException.class, () -> userManager.addUser(null, 500.0));
    }

    // 사용자를 추가하고 이메일로 검색하여 제대로 검색되는지 확인하는 테스트
    @Test
    public void testGetUser() {
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user, 500.0);

        assertNotNull(userManager.getUser("alice@example.com"));
        assertNull(userManager.getUser("bob@example.com"));
    }

    // null 이메일로 사용자를 검색할 때 null을 반환하는지 확인하는 테스트
    @Test
    public void testGetUserWithNullEmail() {
        assertNull(userManager.getUser(null));
    }

    // 사용자 계정을 이메일로 검색하여 제대로 검색되는지 확인하는 테스트
    @Test
    public void testGetUserAccount() {
        User user = new User("Alice", "alice@example.com");
        userManager.addUser(user, 500.0);

        assertNotNull(userManager.getUserAccount("alice@example.com"));
        assertNull(userManager.getUserAccount("bob@example.com"));
    }

    // null 이메일로 사용자 계정을 검색할 때 null을 반환하는지 확인하는 테스트
    @Test
    public void testGetUserAccountWithNullEmail() {
        assertNull(userManager.getUserAccount(null));
    }

    // 사용자를 알림 서비스로 알림을 보내는 테스트
    @Test
    public void testNotifyUser() {
        User user = new User("Bob", "bob@example.com");
        userManager.addUser(user, 300.0);

        boolean result = userManager.notifyUser(user, notificationService, "Test message");

        assertTrue(result);
    }

    // 알림 서비스가 알림을 보내지 못했을 때를 테스트
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

    // null 사용자를 알림 서비스로 알림을 보내려 할 때를 테스트
    @Test
    public void testNotifyUserWithNullUser() {
        boolean result = userManager.notifyUser(null, notificationService, "Test message");
        assertFalse(result);
    }

    // 사용자의 결제를 처리하는 테스트
    @Test
    public void testProcessUserPayment() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("charlie@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);

        assertTrue(result);
    }

    // 잔고가 부족한 사용자의 결제를 처리하려 할 때를 테스트
    @Test
    public void testProcessUserPaymentInsufficientBalance() {
        User user = new User("Dave", "dave@example.com");
        userManager.addUser(user, 50.0);
        UserAccount userAccount = userManager.getUserAccount("dave@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, notificationService);

        assertFalse(result);
    }

    // null 사용자 계정으로 결제를 처리하려 할 때를 테스트
    @Test
    public void testProcessUserPaymentWithNullUserAccount() {
        boolean result = userManager.processUserPayment(null, 100.0, paymentService, notificationService);
        assertFalse(result);
        verify(notificationService, times(1)).sendNotification(null, "Payment of 100.0 failed due to insufficient balance.");
    }

    // null 알림 서비스로 결제를 처리하려 할 때를 테스트
    @Test
    public void testProcessUserPaymentWithNullNotificationService() {
        User user = new User("Charlie", "charlie@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("charlie@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, null);
        assertFalse(result);
    }

    // 유효한 사용자 계정과 null 알림 서비스로 결제를 처리하려 할 때를 테스트
    @Test
    public void testProcessUserPaymentWithValidUserAccountAndNullNotificationService() {
        User user = new User("Eve", "eve@example.com");
        userManager.addUser(user, 200.0);
        UserAccount userAccount = userManager.getUserAccount("eve@example.com");

        boolean result = userManager.processUserPayment(userAccount, 100.0, paymentService, null);
        assertFalse(result);
        verify(notificationService, times(1)).sendNotification(null, "Payment of 100.0 failed due to insufficient balance.");
    }
}


