package com.example.booklend;

public class Notification {
    String title, message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Notification() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
