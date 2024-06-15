package com.example;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean processPayment(UserAccount userAccount, double amount) {
        if (userAccount.getBalance() < amount) {
            System.out.println("Insufficient balance for user " + userAccount.getUser().getEmail());
            return false;
        } else {
            userAccount.setBalance(userAccount.getBalance() - amount);
            System.out.println("Processed payment of " + amount + " for user " + userAccount.getUser().getEmail());
            return true;
        }
    }
}
