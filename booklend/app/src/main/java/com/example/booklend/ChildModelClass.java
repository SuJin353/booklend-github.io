package com.example.booklend;

public class ChildModelClass {
    Book book;
    public ChildModelClass(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
