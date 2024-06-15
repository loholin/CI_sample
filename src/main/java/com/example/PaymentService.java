package com.example;

public interface PaymentService {
    boolean processPayment(UserAccount userAccount, double amount);
}
