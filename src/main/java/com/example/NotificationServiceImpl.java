package com.example;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public boolean sendNotification(User user, String message) {
        if (user == null || message == null || message.isEmpty()) {
            System.out.println("Cannot send notification due to null user or empty message.");
            return false;
        }
        System.out.println("Sending notification to " + user.getEmail() + ": " + message);
        return true;
    }
}


