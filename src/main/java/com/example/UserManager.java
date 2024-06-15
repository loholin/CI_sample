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
        users.add(user);
        userAccounts.add(new UserAccount(user, initialBalance));
    }

    public User getUser(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public UserAccount getUserAccount(String email) {
        for (UserAccount account : userAccounts) {
            if (account.getUser().getEmail().equals(email)) {
                return account;
            }
        }
        return null;
    }

    public boolean notifyUser(User user, NotificationService notificationService, String message) {
        return notificationService.sendNotification(user, message);
    }

    public boolean processUserPayment(UserAccount userAccount, double amount, PaymentService paymentService, NotificationService notificationService) {
        if (paymentService.processPayment(userAccount, amount)) {
            notificationService.sendNotification(userAccount.getUser(), "Payment of " + amount + " processed successfully.");
            return true;
        } else {
            notificationService.sendNotification(userAccount.getUser(), "Payment of " + amount + " failed due to insufficient balance.");
            return false;
        }
    }
}

