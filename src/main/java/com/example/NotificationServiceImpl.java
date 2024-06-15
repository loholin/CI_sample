package com.example;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public boolean sendNotification(User user, String message) {
        // 간단한 알림 전송 로직
        System.out.println("Sending notification to " + user.getEmail() + ": " + message);
        return true;
    }
}

