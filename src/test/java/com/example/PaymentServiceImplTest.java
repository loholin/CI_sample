package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceImplTest {
    private PaymentServiceImpl paymentService = new PaymentServiceImpl();

    @Test
    public void testProcessPaymentSuccess() {
        User user = new User("Test User", "test@example.com");
        UserAccount userAccount = new UserAccount(user, 200.0);

        assertTrue(paymentService.processPayment(userAccount, 100.0));
        assertEquals(100.0, userAccount.getBalance());
    }

    @Test
    public void testProcessPaymentInsufficientBalance() {
        User user = new User("Test User", "test@example.com");
        UserAccount userAccount = new UserAccount(user, 50.0);

        assertFalse(paymentService.processPayment(userAccount, 100.0));
        assertEquals(50.0, userAccount.getBalance());
    }

    @Test
    public void testProcessPaymentNullAccount() {
        assertFalse(paymentService.processPayment(null, 100.0));
    }

    @Test
    public void testProcessPaymentNegativeAmount() {
        User user = new User("Test User", "test@example.com");
        UserAccount userAccount = new UserAccount(user, 100.0);

        assertFalse(paymentService.processPayment(userAccount, -50.0));
        assertEquals(100.0, userAccount.getBalance());
    }
}

