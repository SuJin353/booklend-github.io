package com.example.booklendadmin;

public class User {
    String username, uri, uid;

    public User() {
    }

    public User(String username, String uid, String uri) {
        this.username = username;
        this.uid = uid;
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
