package com.example.comp2000cs;

public class EmployeeNotifications {
    private int id;
    private String title;
    private String message;
    private String timestamp;

    public EmployeeNotifications(int id, String title, String message, String timestamp) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
