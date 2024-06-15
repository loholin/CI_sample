package com.example;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private List<UserAccount> userAccounts;

    public UserManager() {
        this.users = new ArrayList<>();
        this.userAccounts = new ArrayList<>();
    }

    public void addUser(User user, double initialBalance) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        users.add(user);
        userAccounts.add(new UserAccount(user, initialBalance));
    }

    public User getUser(String email) {
        if (email == null) {
            return null;
        }
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public UserAccount getUserAccount(String email) {
        if (email == null) {
            return null;
        }
        for (UserAccount account : userAccounts) {
            if (account.getUser().getEmail().equals(email)) {
                return account;
            }
        }
        return null;
    }

    public boolean notifyUser(User user, NotificationService notificationService, String message) {
        if (user == null) {
            return false;
        }
        return notificationService.sendNotification(user, message);
    }

    public boolean processUserPayment(UserAccount userAccount, double amount, PaymentService paymentService, NotificationService notificationService) {
        if (userAccount == null) {
            if (notificationService != null) {
                notificationService.sendNotification(null, "Payment of " + amount + " failed due to null user account.");
            }
            return false;
        }

        if (notificationService == null) {
            return false;
        }

        if (paymentService == null) {
            notificationService.sendNotification(userAccount.getUser(), "Payment of " + amount + " failed due to null payment service.");
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


