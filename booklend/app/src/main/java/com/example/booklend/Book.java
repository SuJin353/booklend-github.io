package com.example.booklend;

public class Book {
    private String key, imageUri, name, genre, author, description;
    private int price, quantity, borrowed;

    public Book() {

    }
    public Book(String key, String imageUri, String name, String genre, String author, int price, int quantity, String description, int borrowed) {
        this.key = key;
        this.imageUri = imageUri;
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.borrowed = borrowed;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }
}
