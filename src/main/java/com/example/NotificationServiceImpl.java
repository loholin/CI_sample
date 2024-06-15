package com.example;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public boolean sendNotification(User user, String message) {
        // User가 null인 경우 알림을 보내지 않음
        if (user == null) {
            System.out.println("Cannot send notification. User is null.");
            return false;
        }

        // 간단한 알림 전송 로직
        System.out.println("Sending notification to " + user.getEmail() + ": " + message);
        return true;
    }
}

