package com.example;

public class UserAccount {
    private final User user;
    private double balance;

    public UserAccount(User user, double balance) {
        this.user = user;
        this.balance = balance;
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
