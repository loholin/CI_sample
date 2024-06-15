package com.example;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private List<UserAccount> userAccounts = new ArrayList<>();

    public void addUser(User user, double initialBalance) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        users.add(user);
        userAccounts.add(new UserAccount(user, initialBalance));
    }

    public User getUser(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    public UserAccount getUserAccount(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return userAccounts.stream().filter(ua -> ua.getUser().getEmail().equals(email)).findFirst().orElse(null);
    }

    public boolean notifyUser(User user, NotificationService notificationService, String message) {
        if (user == null || notificationService == null || message == null || message.isEmpty()) {
            return false;
        }
        return notificationService.sendNotification(user, message);
    }

    public boolean processUserPayment(UserAccount userAccount, double amount, PaymentService paymentService, NotificationService notificationService) {
        if (userAccount == null || paymentService == null || notificationService == null) {
            if (notificationService != null) {
                notificationService.sendNotification(null, "Payment failed due to null parameter(s)");
            }
            return false;
        }

        if (amount <= 0) {
            notificationService.sendNotification(userAccount.getUser(), "Payment amount must be greater than zero");
            return false;
        }

        if (paymentService.processPayment(userAccount, amount)) {
            notificationService.sendNotification(userAccount.getUser(), "Payment of " + amount + " processed successfully.");
            return true;
        } else {
            notificationService.sendNotification(userAccount.getUser(), "Payment of " + amount + " failed due to insufficient balance.");
            return false;
        }
    }
}



