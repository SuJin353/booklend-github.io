package com.example.booklend;

public class Comment {
    String username, uri, comment, comment_date;

    public Comment() {
    }

    public Comment(String username, String uri, String comment, String comment_date) {
        this.username = username;
        this.uri = uri;
        this.comment = comment;
        this.comment_date = comment_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}
