package com.project.smarthome.smarthome.Model;


public class NotificationDetails {
    private int notificationId;
    private String title;
    private String message;

    // Creates a NotificationDetails object where the notification ID doesn't matter
    // Should only be used when the notification should be replaced by any other new notification
    public NotificationDetails(String title, String message) {
        this.notificationId = 0;
        this.title = title;
        this.message = message;
    }

    // Standard constructor
    public NotificationDetails(int notificationId, String title, String message) {
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
