package com.example.booklend;

public class User {
    String uri, username, fullname, gender, year_of_birth, school, user_class, email, phone_number, password;
    int credit;

    public User() {
    }

    public User(String uri, String username, String fullname, String gender, String year_of_birth, String school, String user_class, String email, String phone_number, String password, int credit) {
        this.uri = uri;
        this.username = username;
        this.fullname = fullname;
        this.gender = gender;
        this.year_of_birth = year_of_birth;
        this.school = school;
        this.user_class = user_class;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.credit = credit;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    public String getUser_class() {
        return user_class;
    }

    public void setUser_class(String user_class) {
        this.user_class = user_class;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(String year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
