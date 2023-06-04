package com.example.booklend;

public class TransactionInfo {
    String key, name, borrowedDateAndTime;
    int price;

    public TransactionInfo() {
    }

    public TransactionInfo(String key, String name, int price, String borrowedDateAndTime) {
        this.key = key;
        this.name = name;
        this.price = price;
        this.borrowedDateAndTime = borrowedDateAndTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBorrowedDateAndTime() {
        return borrowedDateAndTime;
    }

    public void setBorrowedDateAndTime(String borrowedDateAndTime) {
        this.borrowedDateAndTime = borrowedDateAndTime;
    }
}
