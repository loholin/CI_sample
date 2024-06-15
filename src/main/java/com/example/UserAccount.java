package com.example;

public class UserAccount {
    private User user;
    private double balance;

    public UserAccount(User user, double initialBalance) {
        this.user = user;
        this.balance = initialBalance;
    }

    public User getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
